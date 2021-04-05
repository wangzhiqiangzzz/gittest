package com.aops.starter.common.application;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;

public class ApplicationNameBeanPostProcessor implements BeanPostProcessor, PriorityOrdered {
    private final String applicationName;

    public ApplicationNameBeanPostProcessor(String applicationName){
        this.applicationName = applicationName;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationNameAware){
            ((ApplicationNameAware)bean).setApplicationName(applicationName);
        }
        return bean;
    }
    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
