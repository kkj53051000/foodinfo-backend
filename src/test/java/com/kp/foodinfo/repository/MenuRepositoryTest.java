package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.BrandMenuKind;
import com.kp.foodinfo.domain.Food;
import com.kp.foodinfo.domain.Menu;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MenuRepositoryTest {
    @Autowired
    MenuRepository menuRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    BrandMenuKindRepository brandMenuKindRepository;

    @Autowired
    FoodRepository foodRepository;

    @Test
    @Transactional
    void MENU_INSERT_TEST() {
        //given
        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        Brand brand = new Brand("pizzaHut", "test/test.jpg", new Date(), food);
        BrandMenuKind brandMenuKind = new BrandMenuKind("메인메뉴", 1, brand);

        brandRepository.save(brand);
        brandMenuKindRepository.save(brandMenuKind);

        Menu menu = new Menu("abcPizza", 20000, "test/test.jpg", brandMenuKind);

        //when
        menuRepository.save(menu);

        //then
        Assertions.assertEquals(menuRepository.findById(menu.getId()).get(), menu);
    }

    @Test
    @Transactional
    void MENU_SELECT_BY_BRAND_MENU_KIND_TEST() {
        //given
        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        Brand brand = new Brand("pizzaHut", "test/test.jpg", new Date(), food);
        BrandMenuKind brandMenuKind = new BrandMenuKind("메인메뉴", 1, brand);

        brandRepository.save(brand);
        brandMenuKindRepository.save(brandMenuKind);


        Menu menu = new Menu("abcPizza", 20000, "test/test.jpg", brandMenuKind);

        //when
        menuRepository.save(menu);

        //System.out.println("brandmenukind length : " + menuRepository.findByBrandMenuKind(brandMenuKind).size());

        //then
        Assertions.assertEquals(menuRepository.findByBrandMenuKind(brandMenuKind).get(0), menu);
    }
}