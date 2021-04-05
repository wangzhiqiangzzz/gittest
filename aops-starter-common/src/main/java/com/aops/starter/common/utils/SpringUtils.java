package com.aops.starter.common.utils;

import com.aops.starter.common.application.ApplicationNameAware;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringUtils implements ApplicationContextAware, ApplicationNameAware {
    private static ApplicationContext applicationContext;
    private static String appName;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringUtils.applicationContext = applicationContext;
    }

    public static <T>T getBean(Class<T> requiredType){
        return applicationContext.getBean(requiredType);
    }
    public static <T>T getBean(String beanName){
        return (T) applicationContext.getBean(beanName);
    }

    public static String getAppName(){
        return appName;
    }

    @Override
    public void setApplicationName(String applicationName) {
        appName = applicationName;
    }
}
