//package com.kp.foodinfo.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.kp.foodinfo.domain.Brand;
//import com.kp.foodinfo.domain.CollabPlatform;
//import com.kp.foodinfo.domain.Food;
//import com.kp.foodinfo.dto.FileTestUtilControllerDto;
//import com.kp.foodinfo.repository.BrandRepository;
//import com.kp.foodinfo.repository.CollabPlatformRepository;
//import com.kp.foodinfo.repository.FoodRepository;
//import com.kp.foodinfo.service.CollabEventService;
//import com.kp.foodinfo.util.FileTestUtil;
//import com.kp.foodinfo.vo.BasicVo;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//
//import java.io.IOException;
//import java.util.Date;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class CollabEventControllerTest {
//    @Autowired
//    MockMvc mockMvc;
//
//    @Autowired
//    BrandRepository brandRepository;
//
//    @Autowired
//    CollabPlatformRepository collabPlatformRepository;
//
//    @Autowired
//    FoodRepository foodRepository;
//
//    @Test
//    @Transactional
//    public void COLLAB_EVENT_PROCESS_TEST() throws Exception {
//        // CollabEventService Change Mock
//        CollabEventService collabEventService = Mockito.mock(CollabEventService.class);
//        CollabEventController collabEventController = new CollabEventController(collabEventService);
//        mockMvc = MockMvcBuilders.standaloneSetup(collabEventController).build();
//
//        FileTestUtilControllerDto fileRequest = FileTestUtil.getTestMultifileController();
//
//        Food food = new Food("pizza", "/test/test.jpg");
//        foodRepository.save(food);
//
//        Brand brand = new Brand("pizzaHut", "/test/test.jpg", new Date(), food);
//        brandRepository.save(brand);
//
//        CollabPlatform collabPlatform = new CollabPlatform("card", "/img/img.jpg");
//        collabPlatformRepository.save(collabPlatform);
//
//        MultiValueMap<String, String> collabEventRequest = new LinkedMultiValueMap<>();
//
//        collabEventRequest.add("title", "title");
//        collabEventRequest.add("content", "content");
//        collabEventRequest.add("startDate", "2021-01-01");
//        collabEventRequest.add("startTime", "00:00");
//        collabEventRequest.add("endDate", "2021-01-02");
//        collabEventRequest.add("endTime", "00:00");
//        collabEventRequest.add("brand_id", Long.toString(brand.getId()));
//        collabEventRequest.add("collabPlatform_id", Long.toString(collabPlatform.getId()));
//
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        BasicVo basicVo = new BasicVo("success");
//
//        String jsonBasicVo = objectMapper.writeValueAsString(basicVo);
//
//
//        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/admin/collabeventprocess")
//                .file(fileRequest.getFile())
//                .requestAttr("request", fileRequest.getRequest())
//                .params(collabEventRequest))
//                .andExpect(content().string(jsonBasicVo))
//                .andDo(print());
//    }
//}