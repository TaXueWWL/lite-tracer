package com.snowalker.tracer.entity;

import java.io.Serializable;

/**
 * @author wuwl@19pay.com.cn
 * @date 2018-8-30
 * @desc 调用链单个节点实体
 */
public class TraceNode implements Serializable {

    private static final long serialVersionUID = 1382492160860427224L;

    /**全局链路id*/
    private String traceId;
    /**当前节点id*/
    private String spanId;
    /**父节点id，根节点没有父节点*/
    private String parentId = "-1";

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getSpanId() {
        return spanId;
    }

    public void setSpanId(String spanId) {
        this.spanId = spanId;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
