package com.kp.foodinfo.controller;

import com.kp.foodinfo.service.FoodService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FoodControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    FoodService foodService;


    //foodController return값 변경후 TEST
    @Test
    //@Rollback(value = false)
    public void FOOD_UPLOAD_PROCESS_TEST() throws Exception {

        //given 파일 불러오기
        MockHttpServletRequest request = new MockHttpServletRequest();
        String realPath = request.getServletContext().getRealPath("");

        String tempPath = "";

        for(int i=0; i < realPath.indexOf("target"); i++) {
            tempPath += realPath.charAt(i);
        }

        String publicRealPath = tempPath + "src" + File.separator + "main" + File.separator + "resources" + File.separator + "static" + File.separator + "img" + File.separator;

        System.out.println("publicRealPath : " + publicRealPath + "test.jpg");

        Path path = Paths.get(publicRealPath + "test.jpg");

        byte[] content = null;

        try {
            content = Files.readAllBytes(path);
        } catch (final IOException e){

        }

        MockMultipartFile file = new MockMultipartFile("test.jpg", "test.jpg", "multipart/form-data", content);





        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/foodprocess")
                .file(file)
                .param("name", "test")).andExpect(status().isOk())
                .andExpect(content().string("success"))
                .andDo(print());

    }

}