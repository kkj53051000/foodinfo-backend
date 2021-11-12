package com.kp.foodinfo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.Event;
import com.kp.foodinfo.domain.EventType;
import com.kp.foodinfo.dto.FileTestUtilControllerDto;
import com.kp.foodinfo.repository.BrandRepository;
import com.kp.foodinfo.repository.EventRepository;
import com.kp.foodinfo.repository.EventTypeRepository;
import com.kp.foodinfo.service.EventService;
import com.kp.foodinfo.util.FileTestUtil;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.EventListVo;
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
class EventControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    EventTypeRepository eventTypeRepository;

    @Autowired
    EventRepository eventRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Transactional
    public void EVENT_PROCESS_TEST() throws Exception {
        // EventService Change Mock
        EventService eventService = Mockito.mock(EventService.class);
        EventController eventController = new EventController(eventService);
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();


        //given
        FileTestUtilControllerDto fileRequest = FileTestUtil.getTestMultifileController();

        Brand brand = new Brand("pizzaHut", "/test/test.jpg");
        brandRepository.save(brand);

        EventType eventType = new EventType("OPO", "/test/test.jpg");
        eventTypeRepository.save(eventType);

        MultiValueMap<String, String> eventRequest = new LinkedMultiValueMap<>();

        eventRequest.add("title", "title");
        eventRequest.add("content", "content");
        eventRequest.add("startDateStr", "2021-10-10");
        eventRequest.add("startTimeStr", "00:00");
        eventRequest.add("endDateStr", "2021-10-11");
        eventRequest.add("endTimeStr", "00:00");
        eventRequest.add("brand_id", Long.toString(brand.getId()));
        eventRequest.add("eventtype_id", Long.toString(eventType.getId()));

        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/admin/eventprocess")
                .file(fileRequest.getFile())
                .requestAttr("request", fileRequest.getRequest())
                .params(eventRequest))
                .andExpect(content().string(objectMapper.writeValueAsString(new BasicVo("success"))))
                .andDo(print());

    }

    @Test
    @Transactional

    public void EVENT_LIST_TEST() throws Exception {
        //given
        Brand brand = new Brand("pizzaHut", "/test/test.jpg");
        brandRepository.save(brand);

        EventType eventType = new EventType("OPO", "/test/test.jpg");
        eventTypeRepository.save(eventType);

        List<Event> events = new ArrayList<>();

        for(int i = 0; i < 10; i++) {
            Event event = Event.builder()
                    .title("title" + i)
                    .content("content")
                    .img("img")
                    .startDate(new Date())
                    .endDate(new Date())
                    .brand(brand)
                    .eventType(eventType)
                    .build();

            events.add(event);


            eventRepository.save(event);
        }

        //when then
        this.mockMvc.perform(post("/api/eventlist").param("brand_id", Long.toString(brand.getId())))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(new EventListVo(events))))
                .andDo(print());
    }
}