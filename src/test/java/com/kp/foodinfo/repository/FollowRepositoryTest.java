package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FollowRepositoryTest {

    @Autowired
    FollowRepository followRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    FoodRepository foodRepository;

    @Test
    @Transactional
    void FOLLOW_INSERT_TEST() {
        //given
        Date joinDate = new Date();

        User user = new User("test", "test", "test@naver.com", joinDate, Role.USER);
        userRepository.save(user);

        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        Brand brand = new Brand("pizzaHut", "test/test.jpg", food);
        brandRepository.save(brand);

        Follow follow = new Follow(user, brand);

        //when
        followRepository.save(follow);

        //then
        Assertions.assertEquals(followRepository.findById(follow.getId()).get(), follow);
    }

    @Test
    @Transactional
    void FOLLOW_SELECT_BY_BRAND_AND_USER_TEST() {
        //given
        Date joinDate = new Date();

        User user = new User("test", "test", "test@naver.com", joinDate, Role.USER);
        userRepository.save(user);

        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        Brand brand = new Brand("pizzaHut", "test/test.jpg", food);
        brandRepository.save(brand);

        Follow follow = new Follow(user, brand);

        //when
        followRepository.save(follow);

        //then
        Assertions.assertEquals(followRepository.findByBrandAndUser(brand, user).get(), follow);
    }

    @Test
    @Transactional
    void FOLLOW_DELETE_TEST() {
        //given
        Date joinDate = new Date();

        User user1 = new User("test1", "test", "test@naver.com", joinDate, Role.USER);
        User user2 = new User("test2", "test", "test@naver.com", joinDate, Role.USER);
        userRepository.save(user1);
        userRepository.save(user2);

        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        Brand brand = new Brand("pizzaHut", "test/test.jpg", food);
        brandRepository.save(brand);

        Follow follow1 = new Follow(user1, brand);
        Follow follow2 = new Follow(user2, brand);

        followRepository.save(follow1);
        followRepository.save(follow2);

        //when
        followRepository.delete(follow1);

        //then
        Assertions.assertEquals(followRepository.findAll().size(), 1);
    }

    @Test
    @Transactional
    void FOLLOW_SELECT_BY_USER_TEST() {
        //given
        Date joinDate = new Date();

        User user = new User("test", "test", "test@naver.com", joinDate, Role.USER);
        userRepository.save(user);

        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        Brand brand = new Brand("pizzaHut", "test/test.jpg", food);
        brandRepository.save(brand);

        Follow follow = new Follow(user, brand);

        //when
        followRepository.save(follow);

        //then
        Assertions.assertEquals(followRepository.findByUser(user).get(0), follow);
    }
}