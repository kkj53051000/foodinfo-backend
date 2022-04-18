package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.Food;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FoodRepositoryTest {
    @Autowired
    FoodRepository foodRepository;

    @Test
    @Transactional
    void FOOD_INSERT_TEST() {
        //given
        Food food = new Food("pizza", "/test/test/test.jpg");

        //when
        foodRepository.save(food);

        //then
        Assertions.assertEquals(food, foodRepository.findById(food.getId()).get());

    }

    @Test
    @Transactional
    void FOOD_LIST_SELECT_TEST() {
        //given
        List<Food> foods1 = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            Food food = new Food("pizza" + i, "test/test/test.jpg");

            foodRepository.save(food);

            foods1.add(food);
        }

        //when
        List<Food> foods2 = foodRepository.findAll();

        //then
        Assertions.assertEquals(foods1, foods2);

    }

    @Test
    @Transactional
    void FOOD_SELECT_BY_NAME_TEST() {
        //given
        Food food = new Food("pizza", "test/test.jpg");

        //when
        foodRepository.save(food);

        //then
        Assertions.assertEquals(foodRepository.findByName(food.getName()).get(), food);
    }
}