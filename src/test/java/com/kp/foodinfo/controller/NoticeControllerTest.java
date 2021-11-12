package com.kp.foodinfo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kp.foodinfo.domain.Role;
import com.kp.foodinfo.domain.User;
import com.kp.foodinfo.repository.UserRepository;
import com.kp.foodinfo.vo.BasicVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
class NoticeControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Test
    @Transactional
    public void NOTICE_PROCESS_TEST() throws Exception {

        User user = new User("test", "test", "test@naver.com", new Date(), Role.ADMIN);

        userRepository.save(user);

        MultiValueMap<String, String> noticeRequest = new LinkedMultiValueMap<>();

        noticeRequest.add("title", "title");
        noticeRequest.add("content", "content");

        ObjectMapper objectMapper = new ObjectMapper();

        BasicVo basicVo = new BasicVo("success");

        String jsonBasicVo = objectMapper.writeValueAsString(basicVo);

        this.mockMvc.perform(post("/api/admin/noticeprocess")
                .params(noticeRequest)
                .param("user_id", Long.toString(user.getId())))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonBasicVo))
                .andDo(print());
    }
}