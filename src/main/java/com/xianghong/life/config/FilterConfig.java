package com.xianghong.life.config;

import com.google.common.collect.Lists;
import com.xianghong.life.filter.BlackUrlFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.util.List;

/**
 * @author jinxianglu
 */
@Slf4j
@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<BlackUrlFilter> blackUrlFilterRegistration() {
        FilterRegistrationBean<BlackUrlFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new BlackUrlFilter());
        registration.setName("blackUrlFilter");
        List<String> urlPatterns = Lists.newArrayList("/api/*");
        registration.setUrlPatterns(urlPatterns);
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }

}
