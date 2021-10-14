package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.Brand;
import org.junit.jupiter.api.Assertions;
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

    @Test
    @Transactional
    void BRAND_INSERT_TEST() {
        //given
        Brand brand = new Brand("pizzaHut", "test/test.jpg");

        //when
        brandRepository.save(brand);

        //then
        Assertions.assertEquals(brandRepository.findById(brand.getId()).get(), brand);
    }

    @Test
    @Transactional
    void BRAND_SELECT_FIND_ALL_TEST() {
        //given

        List<Brand> brands = new ArrayList<>();

        for(int i = 0; i < 100; i++) {
            Brand brand = new Brand("pizzaHut" + i, "test/test.jpg");

            brands.add(brand);

            //when
            brandRepository.save(brand);
        }

        //then
        Assertions.assertEquals(brandRepository.findAll(), brands);
    }

}