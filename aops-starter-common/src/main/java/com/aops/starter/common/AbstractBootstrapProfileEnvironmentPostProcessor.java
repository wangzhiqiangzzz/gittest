package com.aops.starter.common;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Arrays;
import java.util.List;

public class AbstractBootstrapProfileEnvironmentPostProcessor implements EnvironmentPostProcessor {
    public static final String LOCAL = "local";
    public static final String STG = "stg";
    public static final String PRD = "prd";
    public static final String DEV = "dev";
    public static final String IT = "it";

    public AbstractBootstrapProfileEnvironmentPostProcessor() {
    }



    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        if (this.shouldProcess(environment,application)){
            String[] activeProfiles = environment.getActiveProfiles();
            List<String> profiles = Arrays.asList(activeProfiles);
            if (profiles.contains(PRD)){
                this.onPrd(environment,application);
            }else if(profiles.contains(STG)){
                this.onStg(environment,application);
            }else if (profiles.contains(DEV)){
                this.onDev(environment, application);
            }else if(profiles.contains(LOCAL)){
                this.onLocal(environment, application);
            }
            if (profiles.contains(IT)){
                this.onIntegrationTest(environment, application);
            }
            this.onAll(environment, application);
        }
    }
    void onIntegrationTest(ConfigurableEnvironment env, SpringApplication application){

    }
    void onAll(ConfigurableEnvironment env, SpringApplication application){

    }
    void onLocal(ConfigurableEnvironment env, SpringApplication application){

    }
    void onDev(ConfigurableEnvironment env, SpringApplication application){

    }
    void onStg(ConfigurableEnvironment env, SpringApplication application){

    }
    void onPrd(ConfigurableEnvironment env, SpringApplication application){

    }
    boolean shouldProcess(ConfigurableEnvironment environment, SpringApplication application){
        return true;
    }
}
