package com.kp.foodinfo.interceptor;

import com.kp.foodinfo.domain.Role;
import com.kp.foodinfo.domain.User;
import com.kp.foodinfo.exception.JwtVerifyFailException;
import com.kp.foodinfo.repository.UserRepository;
import com.kp.foodinfo.service.JwtService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Component
public class AdminAuthInterceptor implements HandlerInterceptor {

    private final UserRepository userRepository;

    private final JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

//        System.out.println("---------------AdminAuthInterceptor-----------------");
        Enumeration headerNames = request.getHeaderNames();

        boolean authorizationCheck = false;

        Long userId = null;

        while (headerNames.hasMoreElements()) {
            String name = (String) headerNames.nextElement();
            String value = request.getHeader(name);
//            System.out.println(name + " : " + value + "<br>");


            if (name.equals("Authorization") || name.equals("authorization")) {
                authorizationCheck = true;
                if (value.equals("null")) {
                    log.error("UserAuthInterceptor - JwtVerifyFailException: value equals null");
                    throw new JwtVerifyFailException();
                }

                Map<String, Object> returnValue = jwtService.verifyJWT(value);

                userId = Long.valueOf(String.valueOf(returnValue.get("user_id")));



                // Admin 검증
                if (returnValue == null) {
                    log.error("UserAuthInterceptor - JwtVerifyFailException: returnValue equals null");
                    throw new JwtVerifyFailException();
                }
            }
        }

        // authorization가 없을 때 exception
        if (authorizationCheck == false) {
            log.error("UserAuthInterceptor - JwtVerifyFailException: authorization null");
            throw new JwtVerifyFailException();
        }

//        System.out.println("userId : " + userId);

        User user = userRepository.findById(userId).get();

        if (user.getRole() == Role.ADMIN) {
            return true;
        } else {
            return false;
        }

//        request.setAttribute("userId", userId);
    }
}
