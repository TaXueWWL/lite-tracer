package com.snowalker.tracer.config;

import com.snowalker.tracer.http.RestTemplateWrapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author wuwl@19pay.com.cn
 * @date 2018-8-31
 * @desc
 */
@Configuration
public class RestTemplateWapperConfig {

    @Bean
    public RestTemplateWrapper restTemplateWrapper() {
        return new RestTemplateWrapper();
    }
}
