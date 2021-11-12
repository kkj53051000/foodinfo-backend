package com.kp.foodinfo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.dto.BrandDto;
import com.kp.foodinfo.dto.FileTestUtilControllerDto;
import com.kp.foodinfo.repository.BrandRepository;
import com.kp.foodinfo.service.BrandService;
import com.kp.foodinfo.service.FileService;
import com.kp.foodinfo.util.FileTestUtil;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.BrandListVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
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


    @Test
    @Transactional
    public void BRAND_UPLOAD_PROCESS_TEST() throws Exception {
        //BrandService Change Mock
        BrandService brandService = Mockito.mock(BrandService.class);
        BrandController brandController = new BrandController(brandService);
        mockMvc = MockMvcBuilders.standaloneSetup(brandController).build();


        FileTestUtilControllerDto fileRequest = FileTestUtil.getTestMultifileController();

        ObjectMapper objectMapper = new ObjectMapper();
        BasicVo basicVo = new BasicVo("success");
        String jsonBasicVo = objectMapper.writeValueAsString(basicVo);

        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/admin/brandprocess")
                .file(fileRequest.getFile())
                .requestAttr("request", fileRequest.getRequest())
                .param("name", "test"))
                .andExpect(content().string(jsonBasicVo))
                .andDo(print());

    }

    @Test
    @Transactional
    public void BRAND_GET_LIST_TEST() throws Exception {

        List<Brand> brands = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Brand brand = new Brand("pizzaHut" + i, "test/test.jpg");

            brandRepository.save(brand);

            brands.add(brand);
        }

        BrandListVo brandListVo = new BrandListVo(brands);

        ObjectMapper objectMapper = new ObjectMapper();

        String jsonBrandListVo = objectMapper.writeValueAsString(brandListVo);

        this.mockMvc.perform(post("/api/brandlist"))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonBrandListVo))
                .andDo(print());

    }
}