package com.kp.foodinfo.controller;

import com.kp.foodinfo.argumentresolver.Login;
import com.kp.foodinfo.domain.UserSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Component
@Service
@Controller
@Repository
@RestController
@Slf4j
public class AdminController {
    @GetMapping("/api/check")
    public String test(@Login UserSession userSession) {
        log.info(userSession.toString());

        return "ok";
    }
}
