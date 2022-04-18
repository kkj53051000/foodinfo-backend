package com.kp.foodinfo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kp.foodinfo.domain.*;
import com.kp.foodinfo.repository.*;
import com.kp.foodinfo.service.JwtService;
import com.kp.foodinfo.util.DateFormatTestUtil;
import com.kp.foodinfo.util.DateFormatUtil;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.FollowContentListVo;
import com.kp.foodinfo.vo.FollowContentVo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    JwtService jwtService;

    @Autowired
    EventTypeRepository eventTypeRepository;

    @Autowired
    EventRepository eventRepository;

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private String jwtKey;

    private User user;

    @Autowired
    private WebApplicationContext ctx;

    @BeforeEach
    public void getJwtKey() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx).addFilters(new CharacterEncodingFilter("UTF-8", true)).build();

        String uuid = UUID.randomUUID().toString();
        String emailUuid = "test@naver.com" + uuid;

        this.user = new User("test@nver.com", "test", new Date(), DateFormatUtil.dateToStringProcess(new Date()), new Date(), emailUuid, true, Role.USER, false);
        userRepository.save(user);

        this.jwtKey = jwtService.createToken(user.getId());
    }

    @Test
    @Transactional
    public void FOLLOW_PROCESS_TEST() throws Exception {
        //given

        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        Brand brand = new Brand("pizzaHut", "/test/test.jpg", new Date(), food);
        brandRepository.save(brand);

        ObjectMapper objectMapper = new ObjectMapper();
        BasicVo basicVo = new BasicVo("success");

        String jsonBasicVo = objectMapper.writeValueAsString(basicVo);

        //when then
        this.mockMvc.perform(post("/api/user/followprocess/" + brand.getId()).header(HttpHeaders.AUTHORIZATION, jwtKey))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonBasicVo))
                .andDo(print());
    }

    @Test
    @Transactional
    public void FOLLOW_CANCEL_TEST() throws Exception {
        //given
        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        Brand brand = new Brand("pizzaHut", "/test/test.jpg", new Date(), food);
        brandRepository.save(brand);

        Follow follow = new Follow(user, brand);
        followRepository.save(follow);

        ObjectMapper objectMapper = new ObjectMapper();
        BasicVo basicVo = new BasicVo("success");

        String jsonBasicVo = objectMapper.writeValueAsString(basicVo);

        //when then
        this.mockMvc.perform(post("/api/user/followcancel/" + brand.getId()).header(HttpHeaders.AUTHORIZATION, jwtKey))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonBasicVo))
                .andDo(print());
    }

    @Test
    @Transactional
    public void FOLLOW_BRAND_LIST_TEST() throws Exception {
        //given
        //회원 인증 UUID 생성
//        String uuid = UUID.randomUUID().toString();
//        String emailUuid = "test@naver.com" + uuid;
//
//        User user = new User("test@nver.com", "test", new Date(), emailUuid, true, Role.USER);
//        userRepository.save(user);

        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        Brand brand1 = new Brand("pizzaHut", "/test/test.jpg", new Date(), food);
        Brand brand2 = new Brand("bbq", "/test/test.jpg", new Date(), food);
        brandRepository.save(brand1);
        brandRepository.save(brand2);

        Follow follow1 = new Follow(user, brand1);
        Follow follow2 = new Follow(user, brand2);
        followRepository.save(follow1);
        followRepository.save(follow2);

        EventType eventType = new EventType("test", "test/test.jpg");
        eventTypeRepository.save(eventType);

        Event event = new Event("title", "coontent", "test/test.jpg", new Date(), new Date(), DateFormatUtil.dateToIntegerProcess(new Date()), DateFormatUtil.dateToIntegerProcess(new Date()), brand1, eventType);
        eventRepository.save(event);

        List<FollowContentVo> followContentVos = new ArrayList<>();


        FollowContentVo followContentVo = new FollowContentVo(0, brand1.getId(), brand1.getName(), brand1.getImg(), eventType.getName(), eventType.getImg(), event.getTitle(), event.getContent(), event.getImg(), DateFormatTestUtil.dateToStringDayProcess(event.getStartDate()), DateFormatTestUtil.dateToStringDayProcess(event.getEndDate()), "이벤트");

        followContentVos.add(followContentVo);


        ObjectMapper objectMapper = new ObjectMapper();

        // Date Format 설정
        objectMapper.setDateFormat(dateFormat);

        String jsonFollowContentListVo = objectMapper.writeValueAsString(new FollowContentListVo(followContentVos));

        this.mockMvc.perform(get("/api/user/followallcontentlist")
                        .header(HttpHeaders.AUTHORIZATION, jwtKey))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonFollowContentListVo))
                .andDo(print());
    }
}