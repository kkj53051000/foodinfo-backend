package com.kp.foodinfo.controller;

import com.kp.foodinfo.argumentresolver.Login;
import com.kp.foodinfo.domain.Role;
import com.kp.foodinfo.domain.User;
import com.kp.foodinfo.domain.UserSession;
import com.kp.foodinfo.exception.UserNotFoundException;
import com.kp.foodinfo.request.ChangeUserPwRequest;
import com.kp.foodinfo.request.JoinRequest;
import com.kp.foodinfo.request.LoginRequest;
import com.kp.foodinfo.service.JwtService;
import com.kp.foodinfo.service.MyPageService;
import com.kp.foodinfo.service.UserService;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.HeaderUserInfoVo;
import com.kp.foodinfo.vo.MyPageVo;
import com.kp.foodinfo.vo.UserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.mail.internet.MimeMessage;
import java.util.Random;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    private final JwtService jwtService;

    private final MyPageService myPageService;

    @GetMapping("/mockmvc")
    public String mockMvcTest(String name){
        return name + " Hello";
    }

    @GetMapping("/user/check")
    public BasicVo checkUser() {
        return new BasicVo("success");
    }

    @GetMapping("/admin/check")
    public BasicVo checkAdmin() {
        return new BasicVo("success");
    }

    //회원가입 처리
    @PostMapping("/joinprocess")
    public BasicVo joinProcess(@RequestBody JoinRequest joinRequest){
        log.info("joinProcess() : in");
        log.info("joinProcess() - UserService - saveUser() : run");

        userService.saveUser(joinRequest);

        BasicVo basicVo = new BasicVo("success");

        log.info("joinProcess() : BasicVo return");
        return basicVo;
    }

    //이메일 인증
    @GetMapping("/emailauthprocess")
    public RedirectView emailAuthProcess(@RequestParam("uuid") String uuid) {

        userService.emailAuth(uuid);

        return new RedirectView("http://siktamsik.com/emailauthsuccess");
    }

    @PostMapping("/loginprocess")
    public UserVo loginProcess(@RequestBody LoginRequest loginRequest){
        log.info("loginProcess() : in");
        log.info("loginProcess() - UserService - loginUser() : run");
        User user = userService.loginUser(loginRequest);

        if(user != null){
            log.info("loginProcess() - user != null (Normal)");
            //jwt 발급
            log.info("loginProcess() - JwtService - createToken() : run");
            String jwtKey = jwtService.createToken(user.getId());

            UserVo userVo;

            if(user.getRole() == Role.ADMIN) {
                log.info("loginProcess() - user == Role.ADMIN");
                userVo = new UserVo("success", jwtKey, user.getEmail(), true);
            }else{
                log.info("loginProcess() - user != Role.ADMIN");
                userVo = new UserVo("success", jwtKey, user.getEmail(), false);
            }

            log.info("loginProcess() : UserVo return");
            return userVo;
        }else{
            log.error("loginProcess() : UserNotFoundException()");
            throw new UserNotFoundException();
        }
    }

    @GetMapping("/user/headeruserinfo")
    public HeaderUserInfoVo headerUserInfo(@Login UserSession userSession) {
        log.info("headerUserInfo() : in");

        HeaderUserInfoVo headerUserInfoVo = userService.getHeaderUserInfo(userSession.getUserId());

        log.info("headerUserInfo() : HeaderUserInfoVo return");
        return headerUserInfoVo;
    }

    // MyPage Main info
    @GetMapping("/user/mypageinfo")
    public MyPageVo myPageInfo(@Login UserSession userSession) {
        MyPageVo myPageVo = myPageService.getMyPageInfo(userSession.getUserId());

        return myPageVo;
    }

    // Change Password
    @PostMapping("/user/changeuserpw")
    public BasicVo changeUserPw(@Login UserSession userSession, @RequestBody ChangeUserPwRequest changeUserPwRequest) {
        System.out.println("nowUserPw : " + changeUserPwRequest.getNowUserPw());
        System.out.println("changeUserPw : " + changeUserPwRequest.getChangeUserPw());
        BasicVo basicVo = userService.updateUserPw(userSession.getUserId(), changeUserPwRequest);

        return basicVo;
    }
}