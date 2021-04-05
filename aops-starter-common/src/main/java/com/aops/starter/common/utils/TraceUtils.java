package com.aops.starter.common.utils;

import brave.ScopedSpan;
import brave.Tracer;
import brave.Tracing;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.SpanNamer;
import org.springframework.cloud.sleuth.instrument.async.TraceCallable;
import org.springframework.cloud.sleuth.instrument.async.TraceRunnable;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class TraceUtils {
    private static Tracer staticTracer;
    private static Tracing staticTracing;
    private static SpanNamer staticeSpanNamer;

    @Autowired
    private Tracing tracing;

    @Autowired
    private SpanNamer spanNamer;

    public static ScopedSpan span(String name){
        return staticTracer.startScopedSpan(name);
    }

    public static <T> T runInScope(String name, Supplier<T> supplier){
        ScopedSpan span = TraceUtils.span(name);
        try {
            return supplier.get();
        }finally {
            span.finish();
        }
    }

    public void init(){
      staticTracing =  this.tracing;
      staticTracer = staticTracing.tracer();
      staticeSpanNamer = this.spanNamer;
    }

    public static String getReqId(){
        return MDC.get("traceId");
    }

    public static Runnable traceTask(Runnable delegate){
        return new TraceRunnable(staticTracing,staticeSpanNamer,delegate);
    }

    public static <V> Callable<V> traceTask(Callable<V> delegate){
        return new TraceCallable<>(staticTracing,staticeSpanNamer,delegate);
    }
}
