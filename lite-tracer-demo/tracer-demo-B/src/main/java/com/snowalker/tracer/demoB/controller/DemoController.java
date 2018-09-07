package com.snowalker.tracer.demoB.controller;

import com.snowalker.tracer.context.RequestContext;
import com.snowalker.tracer.log.LoggerFactory;
import com.snowalker.tracer.log.Logger;
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

    private static final com.snowalker.tracer.log.logger.Logger LOGGER = com.snowalker.tracer.log.logger.LoggerFactory.getLogger(DemoController.class);
    private static final Logger LOGGER_WAPPER = LoggerFactory.getLogger(DemoController.class);

    @RequestMapping(value = "trace")
    public String trace(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("[demoB]业务逻辑执行完成");
        LOGGER_WAPPER.info("[demoB]业务逻辑执行完成");

        return "[trace]--traceId="+ RequestContext.getTraceId() +
                ",spanId=" + RequestContext.getSpanId() +
                ",parantSpanId={}" + RequestContext.getParentSpanId() +
                "\nrequestUrl=" + request.getRequestURL();
    }

}
