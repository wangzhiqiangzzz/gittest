package com.aops.starter.common;

import com.aops.starter.common.utils.SpringApplicationUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;

public abstract class AbstractProfileEnvironmentPostProcessor extends AbstractBootstrapProfileEnvironmentPostProcessor{

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment env, SpringApplication application) {
        if (!SpringApplicationUtils.isBootApplication(application)){
            return;
        }
        super.postProcessEnvironment(env, application);
    }
}
