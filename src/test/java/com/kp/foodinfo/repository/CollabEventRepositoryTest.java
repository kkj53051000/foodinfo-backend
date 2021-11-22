package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.CollabEvent;
import com.kp.foodinfo.domain.CollabPlatform;
import com.kp.foodinfo.domain.Food;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CollabEventRepositoryTest {

    @Autowired
    CollabEventRepository collabEventRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    CollabPlatformRepository collabPlatformRepository;

    @Autowired
    FoodRepository foodRepository;

    @Test
    @Transactional
    void COLLAB_EVENT_INSERT_TEST() {
        //given
        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        Brand brand = new Brand("pizzaHut", "test/test.jpg", food);
        brandRepository.save(brand);

        CollabPlatform collabPlatform = new CollabPlatform("card", "test/test.jpg");
        collabPlatformRepository.save(collabPlatform);

        Date startDate = new Date();
        Date endDate = new Date();

        CollabEvent collabEvent = new CollabEvent("onePoint3%Event", "content", "test/test.jpg", startDate, endDate, brand, collabPlatform);

        //when
        collabEventRepository.save(collabEvent);

        //then
        Assertions.assertEquals(collabEventRepository.findById(collabEvent.getId()).get(), collabEvent);
    }

    @Test
    @Transactional
    void COLLAB_EVENT_COUNT_BY_BRAND_AND_COLLAB_PLATFORM_TEST() {
        //given
        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        Brand brand = new Brand("pizzaHut", "test/test.jpg", food);
        brandRepository.save(brand);

        CollabPlatform collabPlatform = new CollabPlatform("card", "test/test.jpg");
        collabPlatformRepository.save(collabPlatform);

        Date startDate = new Date();
        Date endDate = new Date();

        List<CollabEvent> collabEvents = new ArrayList<>();

        for(int i = 0; i < 100; i++){
            CollabEvent collabEvent = new CollabEvent("onePoint3%Event" + i, "content", "test/test.jpg", startDate, endDate, brand, collabPlatform);

            collabEvents.add(collabEvent);

            //when
            collabEventRepository.save(collabEvent);
        }

        //then
        Assertions.assertEquals(collabEventRepository.countByBrandAndCollabPlatform(brand, collabPlatform), collabEvents.size());
    }

    @Test
    @Transactional
    void COLLAB_EVENT_SELECT_BY_BRAND_COLLAB_PLATFORM_TEST() {
        //given
        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        Brand brand = new Brand("pizzaHut", "test/test.jpg", food);
        brandRepository.save(brand);

        CollabPlatform collabPlatform = new CollabPlatform("card", "test/test");
        collabPlatformRepository.save(collabPlatform);

        Date startDate = new Date();
        Date endDate = new Date();

        CollabEvent collabEvent = new CollabEvent("onePoint3%Event", "content", "test/test.jpg", startDate, endDate, brand, collabPlatform);

        //when
        collabEventRepository.save(collabEvent);

        //then
        Assertions.assertEquals(collabEventRepository.findByBrandAndCollabPlatform(brand, collabPlatform).get(0), collabEvent);
    }

    @Test
    @Transactional
    void COLLAB_EVENT_SELECT_BY_BRAND_TEST() {
        //given
        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        Brand brand = new Brand("pizzaHut", "test/test.jpg", food);
        brandRepository.save(brand);

        CollabPlatform collabPlatform = new CollabPlatform("card", "test/test");
        collabPlatformRepository.save(collabPlatform);

        Date startDate = new Date();
        Date endDate = new Date();

        CollabEvent collabEvent = new CollabEvent("onePoint3%Event", "content", "test/test.jpg", startDate, endDate, brand, collabPlatform);

        //when
        collabEventRepository.save(collabEvent);

        //then
        Assertions.assertEquals(collabEventRepository.findByBrand(brand).get(0), collabEvent);
    }
}