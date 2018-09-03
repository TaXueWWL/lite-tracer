package com.snowalker.tracer.demoA;

import com.snowalker.tracer.http.RestTemplateWrapper;
import com.snowalker.tracer.interceptor.TraceInterceptor;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.Http11NioProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
@ComponentScan(basePackages = "com.snowalker")
public class Bootstrap extends WebMvcConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(Bootstrap.class);

    public static void main(String[] args) {
        SpringApplication.run(Bootstrap.class, args);
        LOGGER.info("tracer-demo-A启动完成......");
    }

    /**
     * @author wuwl@19pay.com.cn
     * @date 2017-3-17
     * @describe 优化tomcat线程数目
     */
    class MyTomcatConnectorCustomizer implements TomcatConnectorCustomizer {
        @Override
        public void customize(Connector connector) {
            Http11NioProtocol protocol = (Http11NioProtocol) connector
                    .getProtocolHandler();
            // 设置最大连接数
            protocol.setMaxConnections(2000);
            // 设置最大线程数
            protocol.setMaxThreads(2000);
            protocol.setConnectionTimeout(30000);
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TraceInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public RestTemplateWrapper restTemplateWrapper() {
        return new RestTemplateWrapper();
    }
}
