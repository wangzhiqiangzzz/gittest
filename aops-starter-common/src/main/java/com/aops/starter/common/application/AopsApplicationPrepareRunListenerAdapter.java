package com.aops.starter.common.application;

import org.springframework.boot.SpringApplication;
import org.springframework.core.PriorityOrdered;

public class AopsApplicationPrepareRunListenerAdapter extends BootApplicationRunListenerAdapter implements PriorityOrdered {
    public AopsApplicationPrepareRunListenerAdapter(SpringApplication application, String[] args) {
        super(application, args);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
