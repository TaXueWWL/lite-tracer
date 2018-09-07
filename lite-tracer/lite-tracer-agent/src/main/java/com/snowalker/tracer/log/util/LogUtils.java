package com.snowalker.tracer.log.util;

import java.util.Random;
import java.util.UUID;

/**
 * @author wuwl@19pay.com.cn
 * @date 2018-9-5
 * @desc
 */
public class LogUtils {
    private static ThreadLocal<String> logTransactionId = new ThreadLocal();
    private static ThreadLocal<String> logThreadId = new ThreadLocal();

    public LogUtils() {
    }

    public static synchronized String generateThreadId() {
        return UUID.randomUUID().toString().replace("-", "") + "-" + (new Random()).nextInt();
    }

    public static String getThreadId() {
        return (String)logThreadId.get();
    }

    public static void setThreadId() {
        String threadId = generateThreadId();
        logThreadId.set(threadId);
    }

    public static String generateTransactionId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String getLogTransactionId() {
        return (String)logTransactionId.get();
    }

    public static void setTransactionId() {
        String id = generateTransactionId();
        logTransactionId.set(id);
    }

    public static void main(String[] args) {
        System.out.println(generateTransactionId());
    }
}
