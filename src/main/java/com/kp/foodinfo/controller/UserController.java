package com.kp.foodinfo.controller;

import com.kp.foodinfo.domain.User;
import com.kp.foodinfo.form.JoinForm;
import com.kp.foodinfo.form.LoginForm;
import com.kp.foodinfo.form.UserSessionForm;
import com.kp.foodinfo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    UserService userService;

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
            UserSessionForm userSession = new UserSessionForm(user.getUserid(), user.getNickname(), user.getEmail(), user.getJoinDate(), user.getRole());

            session.setAttribute("user", userSession);

            return "redirect:/";
        }else{
            return "redirect:/loginfail";
        }
    }
}
