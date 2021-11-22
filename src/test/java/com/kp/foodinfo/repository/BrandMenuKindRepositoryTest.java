package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.BrandMenuKind;
import com.kp.foodinfo.domain.Food;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BrandMenuKindRepositoryTest {
    @Autowired
    BrandMenuKindRepository brandMenuKindRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    FoodRepository foodRepository;

    @Test
    @Transactional
    void BRAND_MENU_KIND_INSERT_TEST() {
        //given
        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        Brand brand = new Brand("abcPizza", "test/test/test.jpg", food);
        brandRepository.save(brand);

        BrandMenuKind brandMenuKind = new BrandMenuKind("메인 메뉴", 1, brand);

        //when
        brandMenuKindRepository.save(brandMenuKind);

        //then
        Assertions.assertEquals(brandMenuKindRepository.findById(brandMenuKind.getId()).get(), brandMenuKind);
    }

    @Test
    @Transactional
    void BRAND_MENU_KIND_SELECT_BY_PRIORITY_TEST() {
        //given
        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        Brand brand = new Brand("abcPizza", "test/test/test.jpg", food);
        brandRepository.save(brand);

        BrandMenuKind brandMenuKind = new BrandMenuKind("메인 메뉴", 1, brand);

        //when
        brandMenuKindRepository.save(brandMenuKind);

        //then
        Assertions.assertEquals(brandMenuKindRepository.findByPriority(brandMenuKind.getPriority()), brandMenuKind);
    }
}