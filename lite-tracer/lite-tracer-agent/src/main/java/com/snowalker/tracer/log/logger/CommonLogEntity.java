package com.snowalker.tracer.log.logger;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @author wuwl@19pay.com.cn
 * @date 2018-9-5
 * @desc
 */
public class CommonLogEntity<T> {

    @JSONField(
            ordinal = 1,
            name = "content"
    )
    private T content;

    @JSONField(
            ordinal = 2
    )
    private String trace_id;

    @JSONField(
            ordinal = 3
    )
    private String ip;
    @JSONField(
            ordinal = 4
    )
    private String os;

    @JSONField(
            ordinal = 5
    )
    private long log_index = 0L;
    @JSONField(
            ordinal = 6
    )
    private String thread_id;
    @JSONField(
            ordinal = 7
    )
    private String thread_name;
    @JSONField(
            ordinal = 8
    )
    private String log_date;
    @JSONField(
            ordinal = 9
    )
    private String log_level;
    @JSONField(
            ordinal = 10
    )
    private String file_name;
    @JSONField(
            ordinal = 11
    )
    private int file_line;

    @JSONField(
            ordinal = 12
    )
    private String host_name;

    @JSONField(
            ordinal = 13,
            name = "log_type"
    )
    private String log_type;

    public CommonLogEntity() {
    }

    public CommonLogEntity(String log_level) {
        this.log_level = log_level;
    }

    public String getTrace_id() {
        return trace_id;
    }

    public void setTrace_id(String trace_id) {
        this.trace_id = trace_id;
    }

    public String getThread_id() {
        return this.thread_id;
    }

    public void setThread_id(String thread_id) {
        this.thread_id = thread_id;
    }

    public String getThread_name() {
        return this.thread_name;
    }

    public void setThread_name(String thread_name) {
        this.thread_name = thread_name;
    }

    public void setApp_name(String app_name) {
        app_name = app_name;
    }

    public void setApp_version(String app_version) {
        app_version = app_version;
    }

    public String getHost_name() {
        return this.host_name;
    }

    public void setHost_name(String host_name) {
        this.host_name = host_name;
    }

    public String getIp() {
        return this.ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getOs() {
        return this.os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public long getLog_index() {
        return this.log_index;
    }

    public void setLog_index(long log_index) {
        this.log_index = log_index;
    }

    public String getLog_date() {
        return this.log_date;
    }

    public void setLog_date(String log_date) {
        this.log_date = log_date;
    }

    public String getLog_level() {
        return this.log_level;
    }

    public void setLog_level(String log_level) {
        this.log_level = log_level;
    }

    public String getFile_name() {
        return this.file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }

    public int getFile_line() {
        return this.file_line;
    }

    public void setFile_line(int file_line) {
        this.file_line = file_line;
    }

    public T getContent() {
        return this.content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String getLog_type() {
        return this.log_type;
    }

    public void setLog_type(String log_type) {
        this.log_type = log_type;
    }

}
