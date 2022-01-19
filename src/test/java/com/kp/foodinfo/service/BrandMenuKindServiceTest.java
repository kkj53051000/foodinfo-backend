package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.Food;
import com.kp.foodinfo.repository.BrandMenuKindRepository;
import com.kp.foodinfo.repository.BrandRepository;
import com.kp.foodinfo.repository.FoodRepository;
import com.kp.foodinfo.request.BrandMenuKindRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BrandMenuKindServiceTest {

    @Autowired
    BrandMenuKindService brandMenuKindService;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    BrandMenuKindRepository brandMenuKindRepository;

    @Autowired
    FoodRepository foodRepository;

    @Test
    @Transactional
    void BRAND_MENU_KIND_SAVE_TEST() {
        //given
        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        Brand brand = new Brand("pizzaHut", "test/test.jpg", new Date(), food);
        brandRepository.save(brand);

        BrandMenuKindRequest brandMenuKindRequest = new BrandMenuKindRequest("메인메뉴", 1, brand.getId());

        //when
        brandMenuKindService.saveBrandMenuKind(brandMenuKindRequest);

        //then
        Assertions.assertNotNull(brandMenuKindRepository.findByName(brandMenuKindRequest.getName()).get());
    }
}