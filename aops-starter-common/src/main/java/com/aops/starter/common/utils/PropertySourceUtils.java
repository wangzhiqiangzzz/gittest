package com.aops.starter.common.utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class PropertySourceUtils {
    private static final String AOPS_PROPERTIES = "aops.properties";
    private static final String PRIORITY_PROPERTIES = "priority.properties";
    private static final String INNER_PROPERTIES = "inner.properties";

    public static void put(ConfigurableEnvironment environment,String name,Object object){
        Map<String, Object> location = prepareOrGetDefaultLocation(environment);
        location.put(name,object);
    }

    public static  String getAppName(Environment environment){
        String appName = environment.getProperty("spring.application.name");
        Assert.notNull(appName,"spring.application.name must not be null");
        return appName;
    }

    public static Object get(ConfigurableEnvironment environment,String name){
        Map<String, Object> location = prepareOrGetDefaultLocation(environment);
        return location.get(name);
    }

    private static final String SCAN_PACKAGES = "aops_scan_packages";

    public static List<String> getBasePackages(ConfigurableEnvironment environment){
        Map<String, Object> location = prepareOrGetInnerLocation(environment);
        return (List<String>) location.get(SCAN_PACKAGES);
    }

    @NotNull
    private static Map<String,Object> prepareOrGetInnerLocation(ConfigurableEnvironment environment){
        return prepareOrGetMapSource(environment,INNER_PROPERTIES,MutablePropertySources::addLast);
    }

    public  static void setBasePackages(ConfigurableEnvironment environment,List<String> packages){
        Map<String, Object> location = prepareOrGetInnerLocation(environment);
        location.put(SCAN_PACKAGES,packages);
    }

    protected static final String EXCLUDE_NAME = "spring.autoconfigure.exclude";

    public static void excludeAutoConfiguration(ConfigurableEnvironment env,String name){
        String old = env.getProperty(EXCLUDE_NAME);
        if (StringUtils.hasText(old)){
            putPriority(env,EXCLUDE_NAME,old+","+name);
        }else {
            putPriority(env,EXCLUDE_NAME,name);
        }
    }

    protected static Map<String,Object> prepareOrGetDefaultLocation(ConfigurableEnvironment environment){
        return prepareOrGetMapSource(environment,AOPS_PROPERTIES,MutablePropertySources::addLast);
    }
    public static void putPriority(ConfigurableEnvironment environment,String name,Object object){
        Map<String, Object> location = prepareOrGetPriorityLocation(environment);
        location.put(name,object);
    }

    public static Map<String,Object> prepareOrGetPriorityLocation(ConfigurableEnvironment environment){
        return prepareOrGetMapSource(environment,PRIORITY_PROPERTIES,MutablePropertySources::addFirst);
    }

    public static  Map<String,Object> prepareOrGetMapSource(ConfigurableEnvironment environment, String sourceName, BiConsumer<MutablePropertySources, MapPropertySource> sourceLocFunction){
        MutablePropertySources propertySources = environment.getPropertySources();
        MapPropertySource mapPropertySource = (MapPropertySource)propertySources.get(sourceName);
        Map<String,Object> source;
        if (mapPropertySource == null){
            source = new HashMap<>();
            mapPropertySource = new MapPropertySource(sourceName,source);
            sourceLocFunction.accept(propertySources,mapPropertySource);
        }else {
            source = mapPropertySource.getSource();
        }
        return source;
    }
}

