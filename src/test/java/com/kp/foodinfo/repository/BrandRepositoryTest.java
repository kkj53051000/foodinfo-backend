package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.Food;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BrandRepositoryTest {

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    FoodRepository foodRepository;


    @Test
    @Transactional
    void BRAND_INSERT_TEST() {
        //given
        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        Brand brand = new Brand("pizzaHut", "test/test.jpg", food);

        //when
        brandRepository.save(brand);

        //then
        Assertions.assertEquals(brandRepository.findById(brand.getId()).get(), brand);
    }

    @Test
    @Transactional
    void BRAND_SELECT_FIND_ALL_TEST() {
        //given
        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        List<Brand> brands = new ArrayList<>();

        for(int i = 0; i < 100; i++) {
            Brand brand = new Brand("pizzaHut" + i, "test/test.jpg", food);

            brands.add(brand);

            //when
            brandRepository.save(brand);
        }

        //then
        Assertions.assertEquals(brandRepository.findAll(), brands);
    }

}