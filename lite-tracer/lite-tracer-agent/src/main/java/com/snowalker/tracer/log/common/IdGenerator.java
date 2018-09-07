package com.snowalker.tracer.log.common;

import java.util.UUID;

/**
 * @author wuwl@19pay.com.cn
 * @date 2018-8-31
 * @desc code生成器，基于UUID
 */
public class IdGenerator {

    private IdGenerator() {}

    private static volatile IdGenerator idGenerator;

    public static IdGenerator getInstance() {
        if (idGenerator == null) {
            synchronized (IdGenerator.class) {
                if (idGenerator == null) {
                    idGenerator = new IdGenerator();
                }
            }
        }
        return idGenerator;
    }

    private String nextId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public String nextTraceId() {
        return "TRACE-" + this.nextId();
    }

    public String nextSpanId() {
        return "SPAN-" + this.nextId();
    }

    public String nextParentSpanId() {
        return "PARENTSPAN-" + this.nextId();
    }

}
