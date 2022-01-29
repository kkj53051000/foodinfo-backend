package com.kp.foodinfo.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kp.foodinfo.domain.Role;
import com.kp.foodinfo.domain.User;
import com.kp.foodinfo.repository.UserRepository;
import com.kp.foodinfo.request.JoinRequest;
import com.kp.foodinfo.request.LoginRequest;
import com.kp.foodinfo.service.JwtService;
import com.kp.foodinfo.service.UserService;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.UserVo;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.xml.crypto.Data;

import java.util.Date;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    JwtService jwtService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void mockMvcGetTest() throws Exception {

        MultiValueMap<String, String> info = new LinkedMultiValueMap<>();

        info.add("name", "KSJ");

        this.mockMvc.perform(get("/api/mockmvc")
            .params(info)).andExpect(status().isOk())
            .andExpect(content().string("KSJ Hello"))
            .andDo(print());
    }

    @Test
    @Transactional
    public void USER_JOIN_PROCESS_TEST() throws Exception {

        JoinRequest joinRequest = new JoinRequest("test@naver.com", "test");

        BasicVo basicVo = new BasicVo("success");

        String jsonBasicVo = objectMapper.writeValueAsString(basicVo);

        this.mockMvc.perform(post("/api/joinprocess")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(joinRequest))).andExpect(status().isOk())
                .andExpect(content().string(jsonBasicVo))
                .andDo(print());

    }

    @Test
    @Transactional
    public void USER_LOGIN_PROCESS_TEST() throws Exception {

        JoinRequest joinRequest = new JoinRequest("test@naver.com", "test");

        userService.saveUser(joinRequest);

        User user = userRepository.findByEmail("test@naver.com")
                .get();

        String jwtKey = jwtService.createToken(user.getId());

        //비교할 객체
        UserVo userVo = new UserVo("success", jwtKey, user.getEmail(), true);

        LoginRequest loginRequest = new LoginRequest("test@naver.com", "test");

        String jsonUserVo = objectMapper.writeValueAsString(userVo);


        this.mockMvc.perform(post("/api/loginprocess")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginRequest))).andExpect(status().isOk())
                .andExpect(content().string(jsonUserVo))
                .andDo(print());

    }
}