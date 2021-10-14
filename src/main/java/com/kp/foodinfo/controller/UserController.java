package com.kp.foodinfo.controller;

import com.kp.foodinfo.domain.User;
import com.kp.foodinfo.exception.UserNotFoundException;
import com.kp.foodinfo.request.JoinRequest;
import com.kp.foodinfo.request.LoginRequest;
import com.kp.foodinfo.service.JwtService;
import com.kp.foodinfo.service.UserService;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.UserVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    private final JwtService jwtService;

    @GetMapping("/mockmvc")
    public String mockMvcTest(String name){
        return name + " Hello";
    }

    //회원가입 처리
    @PostMapping("/joinprocess")
    public BasicVo joinProcess(JoinRequest joinRequest){
        userService.saveUser(joinRequest);

        BasicVo basicVo = new BasicVo("success");

        return basicVo;
    }

    @PostMapping("/loginprocess")
    public UserVo loginProcess(LoginRequest loginRequest){

        System.out.println("userid : " + loginRequest.getUserid());
        System.out.println("userpw : " + loginRequest.getUserpw());

        User user = userService.loginUser(loginRequest);

        if(user != null){
            //jwt 발급
            String jwtKey = jwtService.createToken(user.getId());

            UserVo userVo = new UserVo("success", jwtKey);

            return userVo;
        }else{
            throw new UserNotFoundException();
        }
    }




    //로그인 처리 (세션)
    /*
    @PostMapping("/loginprocess")
    public BasicVo loginProcess(LoginForm loginForm, HttpServletRequest request){

        HttpSession session = request.getSession();

        User user = userService.loginUser(loginForm);

        if(user != null){
            //세션
            UserSessionForm userSession = new UserSessionForm(user.getUserid(), user.getUserpw(), user.getEmail(), user.getJoinDate(), user.getRole());

            session.setAttribute("user", userSession);

            BasicVo basicVo = new BasicVo("success");

            return basicVo;
        }else{
            //BasicVo basicVo = new BasicVo("failure");

            throw new UserNotFoundException();
        }
    }
     */
    /*
    public ResponseEntity test() {
        if(true) {
            return new ResponseEntity<>(new BasicVo(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new TestVo(), HttpStatus.OK);
        }
    }


    public BasicVo login___() {
        ////

        if(false) {
            log.info("IdNotExistException");
            throw new IdNotExistException();
        }
        return new BasicVo("success");
    }

    @ExceptionHandler(RuntimeException.class)
    public String test() {
        return "error...";
    }

    @ExceptionHandler(IdNotExistException.class)
    public TestVo handleIdAlreadExist () {
        return new TestVo()
    }
    */
}