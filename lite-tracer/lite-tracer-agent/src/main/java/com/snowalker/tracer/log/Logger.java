package com.snowalker.tracer.log;

import com.snowalker.tracer.context.RequestContext;
import org.slf4j.LoggerFactory;

/**
 * @author wuwl@19pay.com.cn
 * @date 2018-9-6
 * @desc
 */
public class Logger {

    private org.slf4j.Logger logger;

    private String name;

    private Class clazz;

    public Logger(String name) {
        this.name = name;
        logger = LoggerFactory.getLogger(name);
    }

    public Logger(Class clazz) {
        this.clazz = clazz;
        logger = LoggerFactory.getLogger(clazz);
    }

    public void trace(String message) {
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        String className = stacks[2].getClassName();
        String methodName = stacks[2].getMethodName();
        int lineNum = stacks[2].getLineNumber();
        this.logger.trace(message + ",[traceId]=" + RequestContext.getTraceId() + ",[className]=" + className + ",[methodName]=" + methodName  + ",[lineNum]=" + lineNum);
    }


    public void debug(String message) {
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        String className = stacks[2].getClassName();
        String methodName = stacks[2].getMethodName();
        int lineNum = stacks[2].getLineNumber();
        this.logger.debug(message + ",[traceId]=" + RequestContext.getTraceId() + ",[className]=" + className + ",[methodName]=" + methodName + ",[lineNum]=" + lineNum);
    }

    public void info(String message) {
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        String className = stacks[2].getClassName();
        String methodName = stacks[2].getMethodName();
        int lineNum = stacks[2].getLineNumber();
        this.logger.info(message + ",[traceId]=" + RequestContext.getTraceId() + ",[className]=" + className + ",[methodName]=" + methodName + ",[lineNum]=" + lineNum);
    }

    public void warn(String message) {
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        String className = stacks[2].getClassName();
        String methodName = stacks[2].getMethodName();
        int lineNum = stacks[2].getLineNumber();
        this.logger.warn(message + ",[traceId]=" + RequestContext.getTraceId() + ",[className]=" + className + ",[methodName]=" + methodName + ",[lineNum]=" + lineNum);
    }

    public void error(String message) {
        StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
        String className = stacks[2].getClassName();
        String methodName = stacks[2].getMethodName();
        int lineNum = stacks[2].getLineNumber();
        this.logger.error(message + ",[traceId]=" + RequestContext.getTraceId() + ",[className]=" + className + ",[methodName]=" + methodName + ",[lineNum]=" + lineNum);
    }

    public boolean isTraceEnabled() {
        return this.logger.isTraceEnabled();
    }

    public boolean isDebugEnabled() {
        return this.logger.isDebugEnabled();
    }

    public boolean isInfoEnabled() {
        return this.logger.isInfoEnabled();
    }

    public boolean isWarnEnabled() {
        return this.logger.isWarnEnabled();
    }

    public boolean isErrorEnabled() {
        return this.logger.isErrorEnabled();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return null;
    }

    public Class getClazz() {
        return clazz;
    }

    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
}
