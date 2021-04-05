package com.aops.starter.common.infrastructure;

import org.springframework.context.SmartLifecycle;

public abstract class AbstractSmartLifecycle implements SmartLifecycle {
    private volatile boolean running = false;

    @Override
    public void start() {
        doStart();
        running = true;
    }

    protected abstract void doStart();

    @Override
    public void stop() {
        doStop();
        running = false;
    }

    protected abstract void doStop();

    @Override
    public boolean isRunning() {
        return running;
    }
}
