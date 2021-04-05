package com.aops.starter.common.condition;

import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.function.Function;

public abstract class AbstractEnabledSpringBootCondition<T> extends SpringBootCondition {
    private final String prefix;

    private final Function<T,Boolean> enableFunc;

    private final Class<T> actualType;

    private static final  EnabledContionOutcomeCache OUTCOME_CACHE = SimpleMapEnaledContionOutcomeCache.getInstance();

    public AbstractEnabledSpringBootCondition(String prefix,Class<T> actualType,Function<T,Boolean> enableFunc){
        this.prefix = prefix;
        this.enableFunc = enableFunc;
        this.actualType = actualType;
    }

    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return OUTCOME_CACHE.computeIfAbsent(this.getClass(),aClass -> {
            Binder binder = Binder.get(context.getEnvironment());
            BindResult<T> bind = binder.bind(prefix, actualType);
            boolean bound = bind.isBound();
            if (!bound){
                return ConditionOutcome.match();
            }
            T t = bind.get();
            return enableFunc.apply(t)?ConditionOutcome.match() : ConditionOutcome.noMatch(String.format("%s not match",aClass.getSimpleName()));
        });
    }
}
