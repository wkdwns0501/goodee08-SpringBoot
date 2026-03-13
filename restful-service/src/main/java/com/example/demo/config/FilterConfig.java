package com.example.demo.config;

import com.example.demo.filter.AccessKeyFilter;
import com.example.demo.filter.LogFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 수동 설정 클래스 선언
public class FilterConfig {

    @Bean // 메소드의 반환 객체를 스프링 빈으로 등록
    public FilterRegistrationBean<LogFilter> logFilter() {
        FilterRegistrationBean<LogFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new LogFilter());
        bean.addUrlPatterns("/api/*");
        bean.setOrder(1);
        bean.setName("Log Filter");

        return bean;
    }

    @Bean
    public FilterRegistrationBean<AccessKeyFilter> accessKeyFilter() {
        FilterRegistrationBean<AccessKeyFilter> bean = new FilterRegistrationBean<>();
        bean.setFilter(new AccessKeyFilter());
        bean.addUrlPatterns("/api/*");
        bean.setOrder(2);
        bean.setName("Access Key Filter");

        return bean;
    }

}
