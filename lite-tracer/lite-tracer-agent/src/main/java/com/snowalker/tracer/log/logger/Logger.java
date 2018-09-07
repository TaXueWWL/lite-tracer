package com.snowalker.tracer.log.logger;

import com.alibaba.fastjson.JSON;
import com.snowalker.tracer.context.RequestContext;
import com.snowalker.tracer.log.util.LogUtils;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.helpers.MessageFormatter;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wuwl@19pay.com.cn
 * @date 2018-9-5
 * @desc
 */
public class Logger implements org.slf4j.Logger {
    private static final long MAX_INDEX = 2147483647L;

    private static ThreadLocal<CommonLogEntity> commonLogLocal = new ThreadLocal();

    private org.slf4j.Logger delegateLogger;

    Logger(Object param) {
        if(param instanceof Class) {
            this.setLoggerByClass((Class)param);
        } else if(param instanceof String) {
            this.setLoggerByName((String)param);
        } else {
            this.setLoggerDefault(param);
        }

    }

    private void setLoggerByName(String name) {
        this.delegateLogger = LoggerFactory.getLogger(name);
    }

    private void setLoggerByClass(Class clazz) {
        this.delegateLogger = LoggerFactory.getLogger(clazz);
    }

    private void setLoggerDefault(Object param) {
        this.delegateLogger = null;
    }

    public void trace(Object message) {
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "INFO");
            this.delegateLogger.info(this.getLogMessageBody(commonLogEntity));
        }

    }

    public void info(Object message) {
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "INFO");
            this.delegateLogger.info(this.getLogMessageBody(commonLogEntity));
        }

    }

    public void debug(Object message) {
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "DEBUG");
            this.delegateLogger.debug(this.getLogMessageBody(commonLogEntity));
        }

    }

    public void error(Object message) {
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "ERROR");
            this.delegateLogger.error(this.getLogMessageBody(commonLogEntity));
        }

    }

    public void alarm(Object message) {
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "ALARM");
            this.delegateLogger.error(this.getLogMessageBody(commonLogEntity));
        }

    }

    public void warn(Object message) {
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "WARN");
            this.delegateLogger.warn(this.getLogMessageBody(commonLogEntity));
        }

    }

    private CommonLogEntity getCommonLogEntity(Object message, StackTraceElement[] stacks, String methodName) {
        if(message == null) {
            message = null;
        }

        CommonLogEntity commonLogEntity = (CommonLogEntity)commonLogLocal.get();
        if(commonLogEntity == null) {
            commonLogEntity = new CommonLogEntity();
            commonLogEntity.setThread_id(LogUtils.generateThreadId());
            commonLogEntity.setThread_name(Thread.currentThread().getName());
            commonLogEntity.setOs(System.getProperty("os.name").toLowerCase());

            String ipAddress;
            String host_name;
            try {
                ipAddress = InetAddress.getLocalHost().getHostAddress();
                host_name = InetAddress.getLocalHost().getHostName();
            } catch (UnknownHostException var8) {
                ipAddress = "";
                host_name = "";
            }

            commonLogEntity.setIp(ipAddress);
            commonLogEntity.setHost_name(host_name);
            commonLogLocal.set(commonLogEntity);
        }

        commonLogEntity.setLog_index(this.increLogIndexVal(Long.valueOf(commonLogEntity.getLog_index())).longValue());
        commonLogEntity.setLog_date((new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")).format(new Date(System.currentTimeMillis())));
        commonLogEntity.setTrace_id(RequestContext.getTraceId());
        commonLogEntity.setFile_name(stacks[2].getClassName());
        commonLogEntity.setFile_line(stacks[2].getLineNumber());
        commonLogEntity.setLog_level(methodName);
        commonLogEntity.setContent(message);
        return commonLogEntity;
    }

    private String getLogMessageBody(CommonLogEntity commonLogEntity) {
        return JSON.toJSONString(commonLogEntity);
    }

    private Long increLogIndexVal(Long logIndexVal) {
        Long result = Long.valueOf(logIndexVal.longValue() + 1L);
        if(result.longValue() > 2147483647L) {
            result = Long.valueOf(1L);
        }

        return result;
    }

    @Override
    public String getName() {
        return this.delegateLogger.getName();
    }
    @Override
    public boolean isTraceEnabled() {
        return this.delegateLogger.isTraceEnabled();
    }
    @Override
    public void trace(String msg) {
        Object message = this.getLogMsg((Marker)null, msg, (Object[])null, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "TRACE");
            this.delegateLogger.trace(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void trace(String format, Object arg) {
        Object message = this.getLogMsg((Marker)null, format, new Object[]{arg}, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "TRACE");
            this.delegateLogger.trace(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void trace(String format, Object arg1, Object arg2) {
        Object message = this.getLogMsg((Marker)null, format, new Object[]{arg1, arg2}, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "TRACE");
            this.delegateLogger.trace(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void trace(String format, Object... arguments) {
        Object message = this.getLogMsg((Marker)null, format, arguments, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "TRACE");
            this.delegateLogger.trace(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void trace(String msg, Throwable t) {
        Object message = this.getLogMsg((Marker)null, msg, (Object[])null, t);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "TRACE");
            this.delegateLogger.trace(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public boolean isTraceEnabled(Marker marker) {
        return this.delegateLogger.isTraceEnabled(marker);
    }
    @Override
    public void trace(Marker marker, String msg) {
        Object message = this.getLogMsg(marker, msg, (Object[])null, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "TRACE");
            this.delegateLogger.trace(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void trace(Marker marker, String format, Object arg) {
        Object message = this.getLogMsg(marker, format, new Object[]{arg}, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "TRACE");
            this.delegateLogger.trace(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void trace(Marker marker, String format, Object arg1, Object arg2) {
        Object message = this.getLogMsg(marker, format, new Object[]{arg1, arg2}, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "TRACE");
            this.delegateLogger.trace(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void trace(Marker marker, String format, Object... argArray) {
        Object message = this.getLogMsg(marker, format, argArray, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "TRACE");
            this.delegateLogger.trace(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void trace(Marker marker, String msg, Throwable t) {
        Object message = this.getLogMsg(marker, msg, (Object[])null, t);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "TRACE");
            this.delegateLogger.trace(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public boolean isDebugEnabled() {
        return this.delegateLogger.isDebugEnabled();
    }
    @Override
    public void debug(String msg) {
        Object message = this.getLogMsg((Marker)null, msg, (Object[])null, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "DEBUG");
            this.delegateLogger.debug(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void debug(String format, Object arg) {
        Object message = this.getLogMsg((Marker)null, format, new Object[]{arg}, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "DEBUG");
            this.delegateLogger.debug(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void debug(String format, Object arg1, Object arg2) {
        Object message = this.getLogMsg((Marker)null, format, new Object[]{arg1, arg2}, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "DEBUG");
            this.delegateLogger.debug(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void debug(String format, Object... arguments) {
        Object message = this.getLogMsg((Marker)null, format, arguments, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "DEBUG");
            this.delegateLogger.debug(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void debug(String msg, Throwable t) {
        Object message = this.getLogMsg((Marker)null, msg, (Object[])null, t);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "DEBUG");
            this.delegateLogger.debug(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public boolean isDebugEnabled(Marker marker) {
        return this.delegateLogger.isDebugEnabled(marker);
    }
    @Override
    public void debug(Marker marker, String msg) {
        Object message = this.getLogMsg(marker, msg, (Object[])null, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "DEBUG");
            this.delegateLogger.debug(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void debug(Marker marker, String format, Object arg) {
        Object message = this.getLogMsg(marker, format, new Object[]{arg}, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "DEBUG");
            this.delegateLogger.debug(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void debug(Marker marker, String format, Object arg1, Object arg2) {
        Object message = this.getLogMsg(marker, format, new Object[]{arg1, arg2}, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "DEBUG");
            this.delegateLogger.debug(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void debug(Marker marker, String format, Object... argArray) {
        Object message = this.getLogMsg(marker, format, argArray, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "DEBUG");
            this.delegateLogger.debug(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void debug(Marker marker, String msg, Throwable t) {
        Object message = this.getLogMsg(marker, msg, (Object[])null, t);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "DEBUG");
            this.delegateLogger.debug(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public boolean isInfoEnabled() {
        return this.delegateLogger.isInfoEnabled();
    }
    @Override
    public void info(String msg) {
        Object message = this.getLogMsg((Marker)null, msg, (Object[])null, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "INFO");
            this.delegateLogger.info(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void info(String format, Object arg) {
        Object message = this.getLogMsg((Marker)null, format, new Object[]{arg}, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "INFO");
            this.delegateLogger.info(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void info(String format, Object arg1, Object arg2) {
        Object message = this.getLogMsg((Marker)null, format, new Object[]{arg1, arg2}, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "INFO");
            this.delegateLogger.info(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void info(String format, Object... arguments) {
        Object message = this.getLogMsg((Marker)null, format, arguments, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "INFO");
            this.delegateLogger.info(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void info(String msg, Throwable t) {
        Object message = this.getLogMsg((Marker)null, msg, (Object[])null, t);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "INFO");
            this.delegateLogger.info(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public boolean isInfoEnabled(Marker marker) {
        return this.delegateLogger.isInfoEnabled(marker);
    }
    @Override
    public void info(Marker marker, String msg) {
        Object message = this.getLogMsg(marker, msg, (Object[])null, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "INFO");
            this.delegateLogger.info(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void info(Marker marker, String format, Object arg) {
        Object message = this.getLogMsg(marker, format, new Object[]{arg}, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "INFO");
            this.delegateLogger.info(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void info(Marker marker, String format, Object arg1, Object arg2) {
        Object message = this.getLogMsg(marker, format, new Object[]{arg1, arg2}, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "INFO");
            this.delegateLogger.info(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void info(Marker marker, String format, Object... argArray) {
        Object message = this.getLogMsg(marker, format, argArray, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "INFO");
            this.delegateLogger.info(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void info(Marker marker, String msg, Throwable t) {
        Object message = this.getLogMsg(marker, msg, (Object[])null, t);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "INFO");
            this.delegateLogger.info(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public boolean isWarnEnabled() {
        return this.delegateLogger.isWarnEnabled();
    }
    @Override
    public void warn(String msg) {
        Object message = this.getLogMsg((Marker)null, msg, (Object[])null, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "WARN");
            this.delegateLogger.warn(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void warn(String format, Object arg) {
        Object message = this.getLogMsg((Marker)null, format, new Object[]{arg}, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "WARN");
            this.delegateLogger.warn(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void warn(String format, Object arg1, Object arg2) {
        Object message = this.getLogMsg((Marker)null, format, new Object[]{arg1, arg2}, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "WARN");
            this.delegateLogger.warn(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void warn(String format, Object... arguments) {
        Object message = this.getLogMsg((Marker)null, format, arguments, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "WARN");
            this.delegateLogger.warn(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void warn(String msg, Throwable t) {
        Object message = this.getLogMsg((Marker)null, msg, (Object[])null, t);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "WARN");
            this.delegateLogger.warn(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public boolean isWarnEnabled(Marker marker) {
        return this.delegateLogger.isWarnEnabled(marker);
    }
    @Override
    public void warn(Marker marker, String msg) {
        Object message = this.getLogMsg(marker, msg, (Object[])null, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "WARN");
            this.delegateLogger.warn(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void warn(Marker marker, String format, Object arg) {
        Object message = this.getLogMsg(marker, format, new Object[]{arg}, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "WARN");
            this.delegateLogger.warn(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void warn(Marker marker, String format, Object arg1, Object arg2) {
        Object message = this.getLogMsg(marker, format, new Object[]{arg1, arg2}, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "WARN");
            this.delegateLogger.warn(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void warn(Marker marker, String format, Object... argArray) {
        Object message = this.getLogMsg(marker, format, argArray, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "WARN");
            this.delegateLogger.warn(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void warn(Marker marker, String msg, Throwable t) {
        Object message = this.getLogMsg(marker, msg, (Object[])null, t);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "WARN");
            this.delegateLogger.warn(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public boolean isErrorEnabled() {
        return this.delegateLogger.isErrorEnabled();
    }
    @Override
    public void error(String msg) {
        Object message = this.getLogMsg((Marker)null, msg, (Object[])null, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "ERROR");
            this.delegateLogger.error(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void error(String format, Object arg) {
        Object message = this.getLogMsg((Marker)null, format, new Object[]{arg}, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "ERROR");
            this.delegateLogger.error(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void error(String format, Object arg1, Object arg2) {
        Object message = this.getLogMsg((Marker)null, format, new Object[]{arg1, arg2}, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "ERROR");
            this.delegateLogger.error(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void error(String format, Object... arguments) {
        Object message = this.getLogMsg((Marker)null, format, arguments, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "ERROR");
            this.delegateLogger.error(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void error(String msg, Throwable t) {
        Object message = this.getLogMsg((Marker)null, msg, (Object[])null, t);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "ERROR");
            this.delegateLogger.error(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public boolean isErrorEnabled(Marker marker) {
        return this.delegateLogger.isErrorEnabled(marker);
    }
    @Override
    public void error(Marker marker, String msg) {
        Object message = this.getLogMsg(marker, msg, (Object[])null, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "ERROR");
            this.delegateLogger.error(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void error(Marker marker, String format, Object arg) {
        Object message = this.getLogMsg(marker, format, new Object[]{arg}, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "ERROR");
            this.delegateLogger.error(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void error(Marker marker, String format, Object arg1, Object arg2) {
        Object message = this.getLogMsg(marker, format, new Object[]{arg1, arg2}, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "ERROR");
            this.delegateLogger.error(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void error(Marker marker, String format, Object... argArray) {
        Object message = this.getLogMsg(marker, format, argArray, (Throwable)null);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "ERROR");
            this.delegateLogger.error(this.getLogMessageBody(commonLogEntity));
        }

    }
    @Override
    public void error(Marker marker, String msg, Throwable t) {
        Object message = this.getLogMsg(marker, msg, (Object[])null, t);
        if(this.delegateLogger != null) {
            StackTraceElement[] stacks = Thread.currentThread().getStackTrace();
            CommonLogEntity commonLogEntity = this.getCommonLogEntity(message, stacks, "ERROR");
            this.delegateLogger.error(this.getLogMessageBody(commonLogEntity));
        }

    }

    private Object getLogMsg(Marker marker, String format, Object[] argArray, Throwable throwable) {
        return MessageFormatter.arrayFormat(format, argArray, throwable).getMessage();
    }
}
