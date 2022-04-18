package com.kp.foodinfo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.Event;
import com.kp.foodinfo.domain.EventType;
import com.kp.foodinfo.domain.Food;
import com.kp.foodinfo.dto.FileTestUtilControllerDto;
import com.kp.foodinfo.repository.BrandRepository;
import com.kp.foodinfo.repository.EventRepository;
import com.kp.foodinfo.repository.EventTypeRepository;
import com.kp.foodinfo.repository.FoodRepository;
import com.kp.foodinfo.request.EventRequest;
import com.kp.foodinfo.service.EventService;
import com.kp.foodinfo.service.FileService;
import com.kp.foodinfo.util.FileTestUtil;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.EventListVo;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

    @Autowired
    FoodRepository foodRepository;

    @Mock
    FileService fileService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @Transactional
    public void EVENT_PROCESS_TEST() throws Exception {
        //given
        FileTestUtilControllerDto fileRequest = FileTestUtil.getTestMultifileController();

        // EventService - FileService Change Mock
        Mockito.when(fileService.s3UploadProcess(fileRequest.getFile())).thenReturn("/test/test.jpg");
        EventService eventService = new EventService(eventRepository, fileService, brandRepository, eventTypeRepository);
        EventController eventController = new EventController(eventService);
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();

        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        Brand brand = new Brand("pizzaHut", "/test/test.jpg", new Date(), food);
        brandRepository.save(brand);

        EventType eventType = new EventType("OPO", "/test/test.jpg");
        eventTypeRepository.save(eventType);

        EventRequest eventRequest = EventRequest.builder()
                .title("title")
                .content("content")
                .startDateStr("2021-10-10")
                .endDateStr("2021-10-11")
                .brand_id(brand.getId())
                .eventtype_id(eventType.getId())
                .build();


        MockMultipartFile file = new MockMultipartFile("file", fileRequest.getFile().getBytes());
        MockMultipartFile value = new MockMultipartFile("value", "", "application/json", objectMapper.writeValueAsString(eventRequest).getBytes());


        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/admin/eventprocess")
                        .file(file)
                        .file(value))
                .andExpect(content().string(objectMapper.writeValueAsString(new BasicVo("success"))))
                .andDo(print());

    }

    @Test
    @Transactional

    public void EVENT_LIST_TEST() throws Exception {
        //given
        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        Brand brand = new Brand("pizzaHut", "/test/test.jpg", new Date(), food);
        brandRepository.save(brand);

        EventType eventType = new EventType("OPO", "/test/test.jpg");
        eventTypeRepository.save(eventType);

        List<Event> events = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
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
        this.mockMvc.perform(get("/api/eventlist/" + brand.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(new EventListVo(events))))
                .andDo(print());
    }
}