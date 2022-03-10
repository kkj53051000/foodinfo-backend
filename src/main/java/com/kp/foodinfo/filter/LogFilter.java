package com.kp.foodinfo.filter;

import javax.servlet.*;
import java.io.IOException;

import javax.servlet.Filter;

public class LogFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
