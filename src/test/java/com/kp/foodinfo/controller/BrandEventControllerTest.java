package com.kp.foodinfo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.BrandEvent;
import com.kp.foodinfo.dto.FileTestUtilControllerDto;
import com.kp.foodinfo.repository.BrandEventRepository;
import com.kp.foodinfo.repository.BrandRepository;
import com.kp.foodinfo.service.BrandEventService;
import com.kp.foodinfo.util.FileTestUtil;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.BrandEventListVo;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BrandEventControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    BrandEventRepository brandEventRepository;

    @Test
    @Transactional
    public void BRAND_EVENT_UPLOAD_PROCESS_TEST() throws Exception {
        //BrandEventService Change Mock
        BrandEventService brandEventService = Mockito.mock(BrandEventService.class);
        BrandEventController brandEventController = new BrandEventController(brandEventService);
        mockMvc = MockMvcBuilders.standaloneSetup(brandEventController).build();


        FileTestUtilControllerDto fileRequest = FileTestUtil.getTestMultifileController();

        Brand brand = new Brand("pizzaHut", "test/test.jpg");
        brandRepository.save(brand);

        // Inject Constructor (BrandEventRequest)
        MultiValueMap<String, String> brandEventRequest = new LinkedMultiValueMap<>();

        brandEventRequest.add("title", "title");
        brandEventRequest.add("content", "content");
        brandEventRequest.add("startDate", "2021-01-01");
        brandEventRequest.add("startTime", "00:00");
        brandEventRequest.add("endDate", "2021-01-03");
        brandEventRequest.add("endTime", "00:00");
        brandEventRequest.add("brand_id", Long.toString(brand.getId()));

        ObjectMapper objectMapper = new ObjectMapper();

        BasicVo basicVo = new BasicVo("success");

        String jsonBasicVo = objectMapper.writeValueAsString(basicVo);

        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/admin/brandeventprocess")
                .file(fileRequest.getFile())
                .requestAttr("request", fileRequest.getRequest())
                .params(brandEventRequest))
                .andExpect(content().string(jsonBasicVo))
                .andDo(print());
    }

    // Date를 json화하는 과정에서 둘이 다르게 표기됨 결과는 success
    @Test
    @Transactional
    public void BRAND_EVENT_GET_LIST() throws Exception {
        Brand brand = new Brand("pizzaHut", "test/test.jpg");
        brandRepository.save(brand);

        List<BrandEvent> brandEvents = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            BrandEvent brandEvent = new BrandEvent("title" + i, "content", "test/test.jpg", new Date(), new Date(), brand);

            brandEvents.add(brandEvent);

            brandEventRepository.save(brandEvent);
        }

        BrandEventListVo brandEventListVo = new BrandEventListVo(brandEvents);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBrandEventListVo = objectMapper.writeValueAsString(brandEventListVo);

        System.out.println(jsonBrandEventListVo);

        this.mockMvc.perform(post("/api/brandeventlist").param("brand_id", Long.toString(brand.getId())))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonBrandEventListVo))
                .andDo(print());
    }
}