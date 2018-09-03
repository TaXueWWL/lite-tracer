package com.snowalker.tracer.context;

/**
 * @author wuwl@19pay.com.cn
 * @date 2018-8-30
 * @desc 请求上下文，持有TraceNode
 */
public class RequestContext {

    private final static ThreadLocal<String> traceIdThreadLocal = new ThreadLocal<>();
    private final static ThreadLocal<String> spanIdThreadLocal = new ThreadLocal<>();
    private final static ThreadLocal<String> parentSpanIdThreadLocal = new ThreadLocal<>();

    public static void addTraceId(String id) {
        traceIdThreadLocal.set(id);
    }

    public static String getTraceId() {
        return traceIdThreadLocal.get();
    }

    public static void removeTraceId() {
        traceIdThreadLocal.remove();
    }

    public static void addSpanId(String id) {
        spanIdThreadLocal.set(id);
    }

    public static String getSpanId() {
        return spanIdThreadLocal.get();
    }

    public static void removeSpanId() {
        spanIdThreadLocal.remove();
    }

    public static void addParentSpanId(String id) {
        parentSpanIdThreadLocal.set(id);
    }

    public static String getParentSpanId() {
        return parentSpanIdThreadLocal.get();
    }

    public static void removeParentSpanId() {
        parentSpanIdThreadLocal.remove();
    }
}
