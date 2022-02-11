package com.kp.foodinfo.interceptor;

import com.kp.foodinfo.exception.JwtVerifyFailException;
import com.kp.foodinfo.service.JwtService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Map;

@Slf4j
public class AdminAuthInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(request.getMethod().equals("OPTIONS")) {
            return true;
        }

        JwtService jwtService = new JwtService();

        System.out.println("---------------AdminAuthInterceptor-----------------");
        Enumeration headerNames = request.getHeaderNames();

        boolean authorizationCheck = false;

        Long userId = null;

        while(headerNames.hasMoreElements()){
            String name = (String)headerNames.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + " : " + value + "<br>");


            if (name.equals("Authorization") || name.equals("authorization")) {
                authorizationCheck = true;
                System.out.println("value : " + value);
                if (value.equals("null")){
                    log.error("UserAuthInterceptor - JwtVerifyFailException: value equals null");
                    throw new JwtVerifyFailException();
                }

                Map<String, Object> returnValue = jwtService.verifyJWT(value);

                userId = Long.valueOf(String.valueOf(returnValue.get("user_id")));


                // Admin 검증

                if(returnValue == null) {
                    log.error("UserAuthInterceptor - JwtVerifyFailException: returnValue equals null");
                    throw new JwtVerifyFailException();
                }
            }
        }

        // authorization가 없을 때 exception
        if(authorizationCheck == false) {
            log.error("UserAuthInterceptor - JwtVerifyFailException: authorization null");
            throw new JwtVerifyFailException();
        }

        request.setAttribute("userId", userId);

        return true;
    }
}
