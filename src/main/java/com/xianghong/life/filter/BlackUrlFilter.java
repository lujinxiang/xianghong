package com.xianghong.life.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @author jinxianglu
 * @description 黑名单url过滤器
 */
public class BlackUrlFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.println("BlackUrlFilter start");
        chain.doFilter(request, response);
        System.out.println("BlackUrlFilter end");
    }

    @Override
    public void destroy() {

    }
}
