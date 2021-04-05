package com.aops.starter.common;

import com.aops.starter.common.application.ApplicationNameBeanPostProcessor;
import com.aops.starter.common.condition.EnabledContionOutcomeCacheCleaner;
import com.aops.starter.common.infrastructure.CachingMetadataReaderFactoryProvider;
import com.aops.starter.common.utils.PropertySourceUtils;
import com.aops.starter.common.utils.SpringUtils;
import com.aops.starter.common.utils.TraceUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;

@Configuration
@EnableAspectJAutoProxy
public class AopsCommonConfiguration {
    @Bean
    public static CachingMetadataReaderFactoryProvider cachingMetadataReaderFactoryProvider(ApplicationContext applicationContext)throws  Exception{
        return new CachingMetadataReaderFactoryProvider(applicationContext);
    }

    @Bean
    public TraceUtils traceUtils(){
        return new TraceUtils();
    }

    @Bean
    public SpringUtils aopsBeanUtils(){
        return new SpringUtils();
    }

    @Bean
    public static ApplicationNameBeanPostProcessor applicationNameBeanPostProcessor(Environment environment){
        String appName = PropertySourceUtils.getAppName(environment);
        return new ApplicationNameBeanPostProcessor(appName);
    }

    @Bean
    public EnabledContionOutcomeCacheCleaner enabledContionOutcomeCacheCleaner(){
        return new EnabledContionOutcomeCacheCleaner();
    }
}
