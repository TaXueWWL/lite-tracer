package com.snowalker.tracer.interceptor;

import com.snowalker.tracer.common.IdGenerator;
import com.snowalker.tracer.constant.Constants;
import com.snowalker.tracer.context.RequestContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wuwl@19pay.com.cn
 * @date 2018-8-31
 * @desc Trace拦截器
 */
public class TraceInterceptor extends HandlerInterceptorAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraceInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LOGGER.info("[TraceInterceptor]进入Trace拦截器[preHandle()]");
        String traceId = request.getHeader(Constants.HTTP_HEADER_TRACE_ID);
        String lastSpanId = request.getHeader(Constants.HTTP_HEADER_SPAN_ID);
        String parentSpanId = "";
        /**当前SpanId*/
        String spanId = "";
        traceId = validateTraceId(traceId);
        parentSpanId = getParentSpanId(lastSpanId);
        spanId = getSpanId();
        /**将Trace信息写入线程局部变量*/
        saveTraceInfoToRequestContext(traceId, parentSpanId, spanId);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("[TraceInterceptor]当前请求中,TraceId={},SpanId={},ParentSpanId={}", traceId, spanId, parentSpanId);
        }
        return true;
    }

    private void saveTraceInfoToRequestContext(String traceId, String parentSpanId, String spanId) {
        RequestContext.addTraceId(traceId);
        RequestContext.addSpanId(spanId);
        RequestContext.addParentSpanId(parentSpanId);
    }

    /**每一次的SpanId都是新的，直接分配即可*/
    private String getSpanId() {
        String spanId;
        spanId = IdGenerator.getInstance().nextSpanId();
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("[TraceInterceptor]当前请求的SpanId={}", spanId);
        }
        return spanId;
    }

    /**
     * 当前请求的parentSpanId就是上个spanId
     * 如果上一个spanId为空，则表明当前没有父Span，则父Span为-1
     */
    private String getParentSpanId(String lastSpanId) {
        String parentSpanId;
        if (StringUtils.isEmpty(lastSpanId)) {
            parentSpanId = "-1";
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("[TraceInterceptor]首次请求获取到的ParentSpanId不存在，默认分配ParentSpanId={}", parentSpanId);
            }
        } else {
            parentSpanId = lastSpanId;
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("[TraceInterceptor]当前请求获取到的ParentSpanId={}", parentSpanId);
            }
        }
        return parentSpanId;
    }

    /**TraceId默认第一个为空，如果没值则分配一个*/
    private String validateTraceId(String traceId) {
        if (StringUtils.isEmpty(traceId)) {
            traceId = IdGenerator.getInstance().nextTraceId();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("[TraceInterceptor]首次请求未分配TraceId,生成首次TraceId={}", traceId);
            }
        }
        return traceId;
    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestContext.removeTraceId();
        RequestContext.removeSpanId();
        RequestContext.removeParentSpanId();
        LOGGER.info("[TraceInterceptor]进入Trace拦截器[afterCompletion]清理本次请求的trace信息完成");
        return;
    }
}