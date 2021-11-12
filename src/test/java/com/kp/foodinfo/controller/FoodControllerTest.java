package com.kp.foodinfo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kp.foodinfo.domain.Food;
import com.kp.foodinfo.dto.FileTestUtilControllerDto;
import com.kp.foodinfo.repository.FoodRepository;
import com.kp.foodinfo.service.FileService;
import com.kp.foodinfo.service.FoodService;
import com.kp.foodinfo.util.FileTestUtil;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.FoodListVo;
import lombok.Data;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.web.PageableArgumentResolver;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.BDDMockito.*;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FoodControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FoodRepository foodRepository;


    @Test
    @Transactional
    public void FOOD_UPLOAD_PROCESS_TEST() throws Exception {
        // FoodService Change Mock
        FoodService foodService = Mockito.mock(FoodService.class);
        FoodController foodController = new FoodController(foodService);
        mockMvc = MockMvcBuilders.standaloneSetup(foodController).build();


        String test = "test";

        FileTestUtilControllerDto fileRequest = FileTestUtil.getTestMultifileController();

        //Jackson
        ObjectMapper objectMapper = new ObjectMapper();

        BasicVo basicVo = new BasicVo("success");
        String jsonBasicVo = objectMapper.writeValueAsString(basicVo);


        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/admin/foodprocess")
                .file(fileRequest.getFile())
                .requestAttr("request", fileRequest.getRequest())
                .param("name", test))
                .andExpect(content().string(jsonBasicVo))
                .andDo(print());
    }

    @Test
    @Transactional
    public void FOOD_LIST_TEST() throws Exception {

        List<Food> foods = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Food food = new Food("food" + i, "test/test.jpg");
            foodRepository.save(food);

            foods.add(food);
        }

        FoodListVo foodListVo = new FoodListVo(foods);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonFoodListVo = objectMapper.writeValueAsString(foodListVo);


        this.mockMvc.perform(post("/api/foodlist"))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonFoodListVo))
                .andDo(print());

    }

}