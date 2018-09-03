package com.snowalker.tracer.common;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author wuwl@19pay.com.cn
 * @date 2018-8-31
 * @desc
 */
public class TestIdGenerator extends TestCase {

    @Test
    public void testGeneratorId() {
        IdGenerator idGenerator = IdGenerator.getInstance();
        Assert.assertNotNull(idGenerator.nextTraceId());
        Assert.assertNotNull(idGenerator.nextSpanId());
        Assert.assertNotNull(idGenerator.nextParentSpanId());
    }
}
