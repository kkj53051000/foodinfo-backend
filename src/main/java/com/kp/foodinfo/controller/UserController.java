package com.kp.foodinfo.controller;

import com.kp.foodinfo.domain.User;
import com.kp.foodinfo.form.JoinForm;
import com.kp.foodinfo.form.LoginForm;
import com.kp.foodinfo.form.UserSessionForm;
import com.kp.foodinfo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// logback log4j                      slf4j

@Controller
@Slf4j
public class UserController {
    // ==> private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @ResponseBody
    @GetMapping("/abc")
    public String abc() {
        log.info("zzzzzzzzzzzz");
        return "ok";
    }

    @ResponseBody
    @GetMapping("/mockmvc")
    public String mockMvcTest(@RequestParam String name){
        return name + " Hello";
    }

    @GetMapping("/")
    public String mainPage(){
        return "main";
    }

    @GetMapping("/join")
    public String joinPage(){
        return "join";
    }

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }

    @GetMapping("/loginfail")
    public String loginFailPage(){
        return "loginfail";
    }

    @PostMapping("/joinprocess")
    public String joinProcess(JoinForm joinForm){
        userService.saveUser(joinForm);

        return "redirect:/";
    }

    @PostMapping("/loginprocess")
    public String loginProcess(LoginForm loginForm, HttpServletRequest request){

        HttpSession session = request.getSession();

        User user = userService.loginUser(loginForm);

        if(user != null){
            UserSessionForm userSession = new UserSessionForm(user.getUserid(), user.getUserpw(), user.getEmail(), user.getJoinDate(), user.getRole());

            session.setAttribute("user", userSession);

            return "redirect:/";
        }else{
            return "redirect:/loginfail";
        }
    }
}