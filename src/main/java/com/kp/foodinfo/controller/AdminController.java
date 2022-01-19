package com.kp.foodinfo.controller;

import com.kp.foodinfo.argumentresolver.Login;
import com.kp.foodinfo.domain.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@Slf4j
public class AdminController {
    @GetMapping("/api/check")
    public String test(@Login UserSession userSession) {
        log.info(userSession.toString());

        return "ok";
    }

//    @GetMapping("/api/check")
    public String test2(HttpServletRequest httpServletRequest) {
        // httpServletRequest.
        // Long userId = (Long)httpServletRequest.getAttribute("userId");
        // log.info(userSession.toString());

        return "ok";
    }
}
