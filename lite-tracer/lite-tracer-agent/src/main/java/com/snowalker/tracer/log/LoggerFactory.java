package com.snowalker.tracer.log;

/**
 * @author wuwl@19pay.com.cn
 * @date 2018-9-6
 * @desc
 */
public class LoggerFactory {

    public static Logger getLogger(Class clazz) {
        return new Logger(clazz);
    }

    public static Logger getLogger(String name) {
        return new Logger(name);
    }
}
