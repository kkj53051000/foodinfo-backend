package com.kp.foodinfo.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

//@WebFilter(urlPatterns = {"/api/user/*"})
//@Component
public class UserAuthorizationFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    //인가가 필요한 /api/user/ url에 적용
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        System.out.println("in Filter !!!!!");

        HttpServletRequest request = (HttpServletRequest)servletRequest;

        boolean jwtKeyCheck = false;


        Enumeration headerNames = request.getHeaderNames();

        while(headerNames.hasMoreElements()){
            String name = (String)headerNames.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + " : " + value + "<br>");


            if (name.equals("authorization")) {
                System.out.println("value : " + value);
                System.out.println(value.getClass().getName());
            }

            // request header authorization 내용이 null일 경우
            if (name.equals("authorization") && value.equals("null")){
                System.out.println("JwtNotFoundException 1");

                jwtKeyCheck = true;
            }
        }

        // request header authorization 내용이 존재하지 않을 경우
        if(jwtKeyCheck == false) {
            System.out.println("JwtNotFoundException 2");
        }

        //Header Authorization parsing (/api/user)
        //true -> checking
        //false return fail

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}