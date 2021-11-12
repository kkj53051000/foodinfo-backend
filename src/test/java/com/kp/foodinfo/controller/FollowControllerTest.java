package com.kp.foodinfo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kp.foodinfo.domain.*;
import com.kp.foodinfo.repository.*;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.FollowContentListVo;
import com.kp.foodinfo.vo.FollowContentVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.text.SimpleDateFormat;
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
class FollowControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    FollowRepository followRepository;

    @Autowired
    BrandEventRepository brandEventRepository;

    @Autowired
    CollabEventRepository collabEventRepository;

    @Autowired
    CollabPlatformRepository collabPlatformRepository;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Test
    @Transactional
    public void FOLLOW_PROCESS_TEST() throws Exception {
        //given
        User user = new User("test", "test", "test@nver.com", new Date(), Role.USER);
        userRepository.save(user);

        Brand brand = new Brand("pizzaHut", "/test/test.jpg");
        brandRepository.save(brand);

        MultiValueMap<String, String> followRequest = new LinkedMultiValueMap<>();

        followRequest.add("user_id", Long.toString(user.getId()));
        followRequest.add("brand_id", Long.toString(brand.getId()));

        ObjectMapper objectMapper = new ObjectMapper();
        BasicVo basicVo = new BasicVo("success");

        String jsonBasicVo = objectMapper.writeValueAsString(basicVo);

        //when then
        this.mockMvc.perform(post("/api/user/followprocess").params(followRequest))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonBasicVo))
                .andDo(print());
    }

    @Test
    @Transactional
    public void FOLLOW_CANCEL_TEST() throws Exception {
        //given
        User user = new User("test", "test", "test@nver.com", new Date(), Role.USER);
        userRepository.save(user);

        Brand brand = new Brand("pizzaHut", "/test/test.jpg");
        brandRepository.save(brand);

        Follow follow = new Follow(user, brand);
        followRepository.save(follow);

        ObjectMapper objectMapper = new ObjectMapper();
        BasicVo basicVo = new BasicVo("success");

        String jsonBasicVo = objectMapper.writeValueAsString(basicVo);

        //when then
        this.mockMvc.perform(post("/api/user/followcancel")
                .param("user_id", Long.toString(user.getId()))
                .param("brand_id", Long.toString(brand.getId())))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonBasicVo))
                .andDo(print());
    }

    @Test
    @Transactional
    public void FOLLOW_BRAND_LIST_TEST() throws Exception {
        //given
        User user = new User("test", "test", "test@nver.com", new Date(), Role.USER);
        userRepository.save(user);

        Brand brand1 = new Brand("pizzaHut", "/test/test.jpg");
        Brand brand2 = new Brand("bbq", "/test/test.jpg");
        brandRepository.save(brand1);
        brandRepository.save(brand2);

        Follow follow1 = new Follow(user, brand1);
        Follow follow2 = new Follow(user, brand2);
        followRepository.save(follow1);
        followRepository.save(follow2);

        CollabPlatform collabPlatform = new CollabPlatform("card", "/test/test.jpg");
        collabPlatformRepository.save(collabPlatform);

        BrandEvent brandEvent = new BrandEvent("brandEventTitle", "content", "/test/test.jpg", new Date(), new Date(), brand1);
        brandEventRepository.save(brandEvent);

        CollabEvent collabEvent = new CollabEvent("collabEventTitle", "content", "/test/test.jpg", new Date(), new Date(), brand2, collabPlatform);
        collabEventRepository.save(collabEvent);

        List<FollowContentVo> followContentVos = new ArrayList<>();



        FollowContentVo followContentVo1 = new FollowContentVo(brand1.getName(), brand1.getImg(), "brandEvent", null, brandEvent.getTitle(), brandEvent.getContent(), brandEvent.getImg(), dateFormat.format(brandEvent.getStartDate()), dateFormat.format(brandEvent.getEndDate()));
        FollowContentVo followContentVo2 = new FollowContentVo(brand2.getName(), brand2.getImg(), "collabEvent", collabPlatform.getName(), collabEvent.getTitle(), collabEvent.getContent(), collabEvent.getImg(), dateFormat.format(collabEvent.getStartDate()), dateFormat.format(collabEvent.getEndDate()));

        followContentVos.add(followContentVo1);
        followContentVos.add(followContentVo2);

        ObjectMapper objectMapper = new ObjectMapper();

        // Date Format 설정
        objectMapper.setDateFormat(dateFormat);

        String jsonFollowContentListVo = objectMapper.writeValueAsString(new FollowContentListVo(followContentVos));

        this.mockMvc.perform(post("/api/user/followallcontentlist").param("user_id", Long.toString(user.getId())))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonFollowContentListVo))
                .andDo(print());
    }
}