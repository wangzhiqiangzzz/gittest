package com.aops.starter.common.infrastructure;

import com.aops.starter.common.utils.SpringApplicationUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.context.ApplicationListener;

public interface BootApplicationListener<E extends SpringApplicationEvent> extends ApplicationListener<E> {
    @Override
    default void onApplicationEvent(E e){
        SpringApplication springApplication = e.getSpringApplication();
        if (!SpringApplicationUtils.isBootApplication(springApplication)){
            return;
        }
        doOnApplicationEvent(e);
    }

    void doOnApplicationEvent(E e);

}
