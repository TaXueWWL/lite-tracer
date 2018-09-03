package com.snowalker.tracer.http;

import com.snowalker.tracer.constant.Constants;
import com.snowalker.tracer.context.RequestContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

/**
 * @author wuwl@19pay.com.cn
 * @date 2018-8-31
 * @desc 原生RestTemplate包装
 */
@Component
public class RestTemplateWrapper {

    /**
     * 获取包装trace信息之后的RestTemplate
     * @return
     */
    public RestTemplate getRestTemlate() {
        // 使用拦截器包装http header
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(new ArrayList<ClientHttpRequestInterceptor>() {
            {
                add((request, body, execution) -> {
                    String traceId = RequestContext.getTraceId();
                    String spanId = RequestContext.getSpanId();
                    String parentSpanId = RequestContext.getParentSpanId();

                    if (StringUtils.isNotEmpty(traceId)) {
                        request.getHeaders().add(Constants.HTTP_HEADER_TRACE_ID, traceId);
                    }
                    if (StringUtils.isNotEmpty(spanId)) {
                        request.getHeaders().add(Constants.HTTP_HEADER_SPAN_ID, spanId);
                    }
                    if (StringUtils.isNotEmpty(parentSpanId)) {
                        request.getHeaders().add(Constants.HTTP_HEADER_PARENT_SPAN_ID, parentSpanId);
                    }
                    return execution.execute(request, body);
                });
            }
        });

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        // 注意此处需开启缓存，否则会报getBodyInternal方法“getBody not supported”错误
        factory.setBufferRequestBody(true);
        restTemplate.setRequestFactory(factory);
        return restTemplate;
    }


}
