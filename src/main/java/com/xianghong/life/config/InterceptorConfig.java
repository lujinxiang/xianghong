package com.xianghong.life.config;


import com.xianghong.life.interceptor.PermissionHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author jinxianglu
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Bean
    public PermissionHandlerInterceptor permissionHandlerInterceptor() {
        return new PermissionHandlerInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(permissionHandlerInterceptor())
                .addPathPatterns("/api/**");
    }
}
