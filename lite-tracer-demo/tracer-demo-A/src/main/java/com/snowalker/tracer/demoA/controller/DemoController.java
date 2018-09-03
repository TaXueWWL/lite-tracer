package com.snowalker.tracer.demoA.controller;

import com.snowalker.tracer.context.RequestContext;
import com.snowalker.tracer.http.RestTemplateWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

/**
 * @author wuwl@19pay.com.cn
 * @date 2018-8-31
 * @desc
 */
@RestController
public class DemoController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    RestTemplateWrapper restTemplateWrapper;

    @RequestMapping(value = "trace")
    public @ResponseBody String trace(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("[trace]--traceId={},spanId={},parantSpanId={}",
                RequestContext.getTraceId(),
                RequestContext.getSpanId(),
                RequestContext.getParentSpanId());
        String result =
                restTemplateWrapper.getRestTemlate().getForObject("http://localhost:8081/trace", String.class);

        return "Send Message To B,result= " + result;
    }

}
