package com.snowalker.tracer.demoA.controller;

import com.snowalker.tracer.http.RestTemplateWrapper;
import com.snowalker.tracer.log.LoggerFactory;
import com.snowalker.tracer.log.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

    @Autowired
    RestTemplateWrapper restTemplateWrapper;

    @RequestMapping(value = "trace")
    public @ResponseBody String trace(HttpServletRequest request, HttpServletResponse response) {

        LOGGER.info("[demoA]业务执行开始");
        LOGGER_WAPPER.info("[demoA]业务执行开始");
        String result =
                restTemplateWrapper.getRestTemlate().getForObject("http://localhost:8081/trace", String.class);
        return "Send Message To B,result= " + result;
    }

}
