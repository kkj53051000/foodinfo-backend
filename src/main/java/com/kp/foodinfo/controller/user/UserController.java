package com.kp.foodinfo.controller.user;

import com.kp.foodinfo.aop.LogExcutionTime;
import com.kp.foodinfo.argumentresolver.Login;
import com.kp.foodinfo.domain.Role;
import com.kp.foodinfo.domain.User;
import com.kp.foodinfo.domain.UserSession;
import com.kp.foodinfo.exception.UserNotFoundException;
import com.kp.foodinfo.repository.UserRepository;
import com.kp.foodinfo.request.ChangeUserPwRequest;
import com.kp.foodinfo.request.JoinRequest;
import com.kp.foodinfo.request.LoginRequest;
import com.kp.foodinfo.service.JwtService;
import com.kp.foodinfo.service.MyPageService;
import com.kp.foodinfo.service.UserService;
import com.kp.foodinfo.util.DateFormatUtil;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.HeaderUserInfoVo;
import com.kp.foodinfo.vo.MyPageVo;
import com.kp.foodinfo.vo.UserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.annotation.PostConstruct;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @PostConstruct
    void testUser() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String secuPw = encoder.encode("1234");

        User user = userRepository.findByEmail("test").get();

        if (user == null) {
            userRepository.save(new User("test", secuPw, new Date(), DateFormatUtil.dateToStringProcess(new Date()), new Date(), "a", true, Role.ADMIN, false));
        }
    }

    private final UserService userService;

    private final JwtService jwtService;

    private final MyPageService myPageService;

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
    public BasicVo joinProcess(@RequestBody JoinRequest joinRequest) {

        userService.saveUser(joinRequest);

        BasicVo basicVo = new BasicVo("success");

        return basicVo;
    }

    //이메일 인증
    @GetMapping("/emailauthprocess")
    public RedirectView emailAuthProcess(@RequestParam("uuid") String uuid) {

        userService.emailAuth(uuid);

        return new RedirectView("http://siktamsik.com/emailauthsuccess");
    }

    @PostMapping("/loginprocess")
    public UserVo loginProcess(@RequestBody LoginRequest loginRequest) {
        User user = userService.loginUser(loginRequest);

        if (user != null) {
            //jwt 발급
            String jwtKey = jwtService.createToken(user.getId());

            UserVo userVo;

            if (user.getRole() == Role.ADMIN) {
                userVo = new UserVo("success", jwtKey, user.getEmail(), true);
            } else {
                userVo = new UserVo("success", jwtKey, user.getEmail(), false);
            }

            log.info("loginUser : userId = " + user.getId());
            return userVo;
        } else {
            throw new UserNotFoundException();
        }
    }

    @GetMapping("/user/headeruserinfo")
    public HeaderUserInfoVo headerUserInfo(@Login UserSession userSession) {
        HeaderUserInfoVo headerUserInfoVo = userService.getHeaderUserInfo(userSession.getUserId());

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
        BasicVo basicVo = userService.updateUserPw(userSession.getUserId(), changeUserPwRequest);

        return basicVo;
    }

    @PostMapping("/user/deleteuser/{id}")
    public BasicVo deleteUser(@Login UserSession userSession, @PathVariable("id") long userId) {
        return userService.deleteUser(userId, userSession.getUserId());
    }
}