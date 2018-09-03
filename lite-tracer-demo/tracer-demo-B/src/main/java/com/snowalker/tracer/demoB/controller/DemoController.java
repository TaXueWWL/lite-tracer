package com.snowalker.tracer.demoB.controller;

import com.snowalker.tracer.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wuwl@19pay.com.cn
 * @date 2018-8-31
 * @desc
 */
@RestController
public class DemoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

    @RequestMapping(value = "trace")
    public String trace(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("[trace]--traceId={},spanId={},parantSpanId={}",
                RequestContext.getTraceId(),
                RequestContext.getSpanId(),
                RequestContext.getParentSpanId());
        return "[trace]--traceId="+ RequestContext.getTraceId() +
                ",spanId=" + RequestContext.getSpanId() +
                ",parantSpanId={}" + RequestContext.getParentSpanId() +
                "\nrequestUrl=" + request.getRequestURL();
    }

}
