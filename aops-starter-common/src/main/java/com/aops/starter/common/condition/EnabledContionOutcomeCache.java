package com.aops.starter.common.condition;

import org.springframework.boot.autoconfigure.condition.ConditionOutcome;

import java.util.function.Function;

public interface EnabledContionOutcomeCache {
    ConditionOutcome computeIfAbsent(Class clazz, Function<Class,ConditionOutcome> mappingFunction);

    void clear();
}
