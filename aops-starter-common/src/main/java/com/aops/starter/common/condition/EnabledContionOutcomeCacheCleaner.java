package com.aops.starter.common.condition;

import com.aops.starter.common.infrastructure.BootApplicationListener;
import org.springframework.boot.context.event.ApplicationStartedEvent;

public class EnabledContionOutcomeCacheCleaner implements BootApplicationListener<ApplicationStartedEvent> {
    @Override
    public void doOnApplicationEvent(ApplicationStartedEvent event) {
        SimpleMapEnaledContionOutcomeCache.getInstance().clear();
    }
}
