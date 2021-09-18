package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.User;
import com.kp.foodinfo.form.JoinForm;
import com.kp.foodinfo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService userService;


    @Test
    void saveUserTest(){
        // given
        JoinForm joinForm = new JoinForm("test", "test", "test@naver.com");


        // when
        userService.saveUser(joinForm);


        // then
    }
}