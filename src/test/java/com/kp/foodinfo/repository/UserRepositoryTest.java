package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.Role;
import com.kp.foodinfo.domain.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    void USER_CREATE_TEST() {

        Date now = new Date();
        //given
        User user = new User("test", "test", "test@naver.com", now, Role.USER);

        //when
        userRepository.save(user);

        //then
        Assertions.assertThat(user.getId()).isNotNull();
    }

    @Test
    @Transactional
    void USER_FIND_USERID() {

        Date now = new Date();
        User user = new User("test", "test", "test@naver.com", now, Role.USER);

        userRepository.save(user);

        Assertions.assertThat(userRepository.findByUserid("test")).isEqualTo(user);
    }
}