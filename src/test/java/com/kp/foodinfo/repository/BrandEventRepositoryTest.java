package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.BrandEvent;
import com.kp.foodinfo.domain.Food;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BrandEventRepositoryTest {

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    BrandEventRepository brandEventRepository;

    @Autowired
    FoodRepository foodRepository;

    @Test
    @Transactional
    void BRAND_EVENT_INSERT_TEST() {
        //given
        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        Brand brand = new Brand("pizzaHut", "test/test.jpg", new Date(), food);

        brandRepository.save(brand);

        Date startDate = new Date();
        Date endDate = new Date();

        BrandEvent brandEvent = new BrandEvent("1+1 event", "content", "tes/test.jpg", startDate, endDate, brand);

        //when
        brandEventRepository.save(brandEvent);


        //then
        Assertions.assertEquals(brandEventRepository.findById(brandEvent.getId()).get(), brandEvent);
    }

    @Test
    @Transactional
    void BRAND_EVENT_SELECT_BY_BRAND_TEST() {
        //given
        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        Brand brand = new Brand("pizzaHut", "test/test.jpg", new Date(), food);

        brandRepository.save(brand);

        Date startDate = new Date();
        Date endDate = new Date();

        BrandEvent brandEvent = new BrandEvent("1+1 event", "content", "tes/test.jpg", startDate, endDate, brand);

        //when
        brandEventRepository.save(brandEvent);


        //then
        Assertions.assertEquals(brandEventRepository.findByBrand(brand).get(0), brandEvent);
    }

}