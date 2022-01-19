package com.kp.foodinfo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.Food;
import com.kp.foodinfo.dto.BrandDto;
import com.kp.foodinfo.dto.FileTestUtilControllerDto;
import com.kp.foodinfo.repository.BrandRepository;
import com.kp.foodinfo.repository.FoodRepository;
import com.kp.foodinfo.request.BrandRequest;
import com.kp.foodinfo.service.BrandService;
import com.kp.foodinfo.service.FileService;
import com.kp.foodinfo.util.FileTestUtil;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.BrandListVo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BrandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private FoodRepository foodRepository;


    ObjectMapper objectMapper = new ObjectMapper();

    @PostConstruct
    public void test() {
        System.out.println("123");
    }

    @BeforeEach
    public void init() {
        // BrandService - FileService Change Mock
        FileService fileService = Mockito.mock(FileService.class);
        BrandService brandService = new BrandService(brandRepository, foodRepository, fileService);
        BrandController brandController = new BrandController(brandService);
        mockMvc = MockMvcBuilders.standaloneSetup(brandController).build();
    }

    @Test
    @Transactional
    public void BRAND_UPLOAD_PROCESS_TEST() throws Exception {

        Food food = new Food("pizza", "test.jpg");
        foodRepository.save(food);

        BrandRequest brandRequest = new BrandRequest("test", food.getId());

        FileTestUtilControllerDto fileRequest = FileTestUtil.getTestMultifileController();

        BasicVo basicVo = new BasicVo("success");
        String jsonBasicVo = objectMapper.writeValueAsString(basicVo);

        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/admin/brandprocess")
                .file(fileRequest.getFile())
                .requestAttr("request", fileRequest.getRequest())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(brandRequest)))
                .andExpect(content().string(jsonBasicVo))
                .andDo(print());

    }

    @Test
    @Transactional
    public void BRAND_GET_LIST_TEST() throws Exception {
        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        List<Brand> brands = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Brand brand = new Brand("pizzaHut" + i, "test/test.jpg", new Date(), food);

            brandRepository.save(brand);

            brands.add(brand);
        }

        BrandListVo brandListVo = new BrandListVo(brands);

        ObjectMapper objectMapper = new ObjectMapper();

        String jsonBrandListVo = objectMapper.writeValueAsString(brandListVo);

        this.mockMvc.perform(post("/api/brandlist/" + food.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonBrandListVo))
                .andDo(print());

    }
}