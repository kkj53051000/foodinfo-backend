package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.User;
import com.kp.foodinfo.request.JoinRequest;
import com.kp.foodinfo.request.LoginRequest;
import com.kp.foodinfo.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;


    @Test
    void USER_SAVE_TEST(){
        // given
        JoinRequest joinForm = new JoinRequest("test@naver.com", "test");

        // when
        userService.saveUser(joinForm);

        // then
        Assertions.assertNotNull(userRepository.findByEmail("test@naver.com"));
    }

    @Test
    //@Rollback(value = false)
    void USER_LOGIN_TEST() {
        //given
        JoinRequest joinForm = new JoinRequest("test@naver.com", "test");
        userService.saveUser(joinForm);

        LoginRequest loginForm = new LoginRequest("test", "test");

        //when
        User user = userService.loginUser(loginForm);

        //then
        Assertions.assertNotNull(user);
    }
}