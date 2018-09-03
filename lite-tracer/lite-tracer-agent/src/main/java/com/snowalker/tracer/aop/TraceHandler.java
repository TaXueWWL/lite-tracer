package com.snowalker.tracer.aop;

import com.snowalker.tracer.context.RequestContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author wuwl@19pay.com.cn
 * @date 2018-8-30
 * @desc 请求拦截切面
 */
@Aspect
@Component
public class TraceHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraceHandler.class);

    @Pointcut("execution(* com..*.*controller..*.*(..))")
    public void traceAspect() {}

    @Around("traceAspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        String traceId = RequestContext.getTraceId();
        String spanId = RequestContext.getSpanId();
        String parentSpanId = RequestContext.getParentSpanId();
        String methodName = joinPoint.getSignature().getName();
        Object object = null;
        try {
            LOGGER.info("traceId={},spanId={},parentSpanId={},方法执行[开始],methodName={}",
                    traceId, spanId, parentSpanId, methodName);
            // TODO 执行前发送
            object = joinPoint.proceed();
            // TODO 执行后发送
            LOGGER.info("traceId={},spanId={},parentSpanId={},方法执行[结束],methodName={}",
                    traceId, spanId, parentSpanId, methodName);
        } catch (Throwable t) {
            LOGGER.error("traceId={},spanId={},parentSpanId={},方法执行[异常],methodName={}",
                    traceId, spanId, parentSpanId, methodName);
        }
        return object;
    }
}
