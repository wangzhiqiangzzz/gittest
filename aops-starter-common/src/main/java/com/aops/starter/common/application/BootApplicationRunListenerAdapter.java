package com.aops.starter.common.application;

import com.aops.starter.common.utils.SpringApplicationUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class BootApplicationRunListenerAdapter extends ApplicationRunListenerAdapter{
    private boolean bootApp;

    public BootApplicationRunListenerAdapter(SpringApplication application, String[] args) {
        super(application, args);
    }

    private void  init(){
        bootApp = SpringApplicationUtils.isBootApplication(getApplication());
    }

    @Override
    public void starting() {
        init();
        if (bootApp){
            doStarting();
        }
    }

    protected void doStarting() {

    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        if (bootApp){
            doEnvironmentPrepared(environment);
        }

    }

    private void doEnvironmentPrepared(ConfigurableEnvironment environment) {
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        super.contextPrepared(context);
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        if (bootApp){
            doContextLoaded(context);
        }

    }

    private void doContextLoaded(ConfigurableApplicationContext context) {
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        if (bootApp){
            doStarted(context);
        }

    }

    private void doStarted(ConfigurableApplicationContext context) {
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        if(bootApp){
            doRunning(context);
        }

    }

    private void doRunning(ConfigurableApplicationContext context) {
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        if (bootApp){
            doFailed(context, exception);
        }

    }

    private void doFailed(ConfigurableApplicationContext context, Throwable exception) {

    }
}
