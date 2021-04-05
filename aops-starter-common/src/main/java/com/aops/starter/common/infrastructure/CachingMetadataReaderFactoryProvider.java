package com.aops.starter.common.infrastructure;

import com.aops.starter.common.utils.PropertySourceUtils;
import com.esotericsoftware.minlog.Log;
import org.springframework.boot.type.classreading.ConcurrentReferenceCachingMetadataReaderFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternUtils;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.*;
import java.util.function.Consumer;

public class CachingMetadataReaderFactoryProvider implements ApplicationListener<ContextRefreshedEvent> {
    private ConcurrentReferenceCachingMetadataReaderFactory metadataReaderFactory;

    private ResourcePatternResolver resourcePatternResolver;

    private volatile  boolean cleared = false;

    private final ApplicationContext applicationContext;

    private final Map<String, MetadataReader> cache = new HashMap<>();

    public static final String BEAN_NAME = "org.springframework.boot.autoconfigure.internalCachingMetadataReaderFactory";

    public CachingMetadataReaderFactoryProvider(ApplicationContext applicationContext)throws Exception{
        this.applicationContext = applicationContext;
        init();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (metadataReaderFactory != null && !cleared && event.getApplicationContext() == this.applicationContext){
            metadataReaderFactory.clearCache();
            cache.clear();
            cleared = true;
        }
    }

    public void init()throws Exception{
        try {
            this.metadataReaderFactory = (ConcurrentReferenceCachingMetadataReaderFactory) this.applicationContext.getAutowireCapableBeanFactory().getBean(
                    BEAN_NAME, MetadataReaderFactory.class
            );
        }catch (Exception e){
            this.metadataReaderFactory = new ConcurrentReferenceCachingMetadataReaderFactory(applicationContext);
        }

        this.resourcePatternResolver  = ResourcePatternUtils.getResourcePatternResolver(applicationContext);
        List<String> basePackages = PropertySourceUtils.getBasePackages((ConfigurableEnvironment) applicationContext.getEnvironment());
        List<Resource> resources = getResourcesFromPackages(basePackages);
        distinctProcessResources(metadataReader -> cache.put(metadataReader.getClassMetadata().getClassName(),metadataReader),resources);
    }

    public MetadataReader getClassMetadata(String className){
        return cache.get(className);
    }

    public void processMetadataReader(Consumer<MetadataReader> consumer){
        for (Map.Entry<String,MetadataReader> entry : cache.entrySet()){
            consumer.accept(entry.getValue());
        }
    }
    public Map<String , MetadataReader> getAllMetadata(){
        return cache;
    }

    protected void distinctProcessResources(Consumer<MetadataReader> consumer,List<Resource> resources)throws IOException{
        HashSet<String> scannedRes = new HashSet<>();
        for (Resource resource : resources){
            String uriStr = getUriStr(resource);
            try {
                if (!scannedRes.add(uriStr)){
                    continue;
                }
                doProcessResource(consumer,resource);
            }catch (IOException e){
                Log.warn(".....");
            }
        }
    }

    private void doProcessResource(Consumer<MetadataReader> consumer, Resource resource) throws IOException {
        if (resource.isReadable()){
            MetadataReader metadataReader = this.metadataReaderFactory.getMetadataReader(resource);
            cache.put(metadataReader.getClassMetadata().getClassName(),metadataReader);
        }
    }

    private String getUriStr(Resource resource) throws IOException {
        return resource.getURI().toString();
    }

    private List<Resource> getResourcesFromPackages(List<String> pacakages) throws IOException {
        List<Resource> resources = new LinkedList<>();
        for (String pacakage : pacakages){
            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX+resolveBasePackage(pacakage)+"/**/*.class";
            resources.addAll(Arrays.asList(resourcePatternResolver.getResources(packageSearchPath)));
        }
        return resources;
    }

    protected String resolveBasePackage(String basePackage) {
        return ClassUtils.convertClassNameToResourcePath(this.applicationContext.getEnvironment().resolveRequiredPlaceholders(basePackage));
    }
}
