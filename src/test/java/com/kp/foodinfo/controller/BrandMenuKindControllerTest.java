package com.kp.foodinfo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.Food;
import com.kp.foodinfo.repository.BrandRepository;
import com.kp.foodinfo.repository.FoodRepository;
import com.kp.foodinfo.request.BrandMenuKindRequest;
import com.kp.foodinfo.vo.BasicVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BrandMenuKindControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    FoodRepository foodRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Transactional
    public void BRAND_MENU_KIND_UPLOAD_PROCESS_TEST() throws Exception {
        //given
        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        Brand brand = new Brand("pizzaHut", "test/test.jpg", new Date(), food);
        brandRepository.save(brand);

        BrandMenuKindRequest brandMenuKindRequest = new BrandMenuKindRequest("main", 1, brand.getId());

        BasicVo basicVo = new BasicVo("success");

        String jsonBasicVo = objectMapper.writeValueAsString(basicVo);

        //when then
        this.mockMvc.perform(post("/api/admin/brandmenukindprocess")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(brandMenuKindRequest)))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonBasicVo))
                .andDo(print());

    }
}