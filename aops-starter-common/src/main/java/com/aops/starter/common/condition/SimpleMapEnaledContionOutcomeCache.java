package com.aops.starter.common.condition;

import org.springframework.boot.autoconfigure.condition.ConditionOutcome;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class SimpleMapEnaledContionOutcomeCache implements EnabledContionOutcomeCache{

    private static SimpleMapEnaledContionOutcomeCache instance = new SimpleMapEnaledContionOutcomeCache();

    public static SimpleMapEnaledContionOutcomeCache getInstance(){
        return instance;
    }

    private final ConcurrentHashMap<Class,ConditionOutcome> cache = new ConcurrentHashMap<>();
    @Override
    public ConditionOutcome computeIfAbsent(Class clazz, Function<Class, ConditionOutcome> mappingFunction) {
        return cache.computeIfAbsent(clazz,mappingFunction);
    }

    @Override
    public void clear() {
        cache.clear();
    }
}
