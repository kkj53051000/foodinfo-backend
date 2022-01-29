package com.kp.foodinfo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kp.foodinfo.domain.EventType;
import com.kp.foodinfo.dto.FileTestUtilControllerDto;
import com.kp.foodinfo.repository.EventTypeRepository;
import com.kp.foodinfo.request.EventTypeRequest;
import com.kp.foodinfo.service.EventTypeService;
import com.kp.foodinfo.util.FileTestUtil;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.EventTypeListVo;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EventTypeControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    EventTypeRepository eventTypeRepository;

    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    @Transactional
    public void EVENT_TYPE_PROCESS_TEST() throws Exception {
        //given
        EventTypeService eventTypeService = Mockito.mock(EventTypeService.class);
        EventTypeController eventTypeController = new EventTypeController(eventTypeService);
        mockMvc = MockMvcBuilders.standaloneSetup(eventTypeController).build();

        FileTestUtilControllerDto fileRequest = FileTestUtil.getTestMultifileController();

        String name = "test";

        EventTypeRequest eventTypeRequest = new EventTypeRequest("test");

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBasicVo = objectMapper.writeValueAsString(new BasicVo("success"));


        MockMultipartFile file = new MockMultipartFile("file", fileRequest.getFile().getBytes());
        MockMultipartFile value = new MockMultipartFile("value", "", "application/json", objectMapper.writeValueAsString(eventTypeRequest).getBytes());

        //when then
//        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/admin/eventtypeprocess")
//                .file(fileRequest.getFile())
//                .requestAttr("request", fileRequest.getRequest())
//                .param("name", name))
//                .andExpect(content().string(jsonBasicVo))
//                .andDo(print());

        this.mockMvc.perform(MockMvcRequestBuilders.multipart("/api/admin/eventtypeprocess")
                .file(file)
                .file(value))
                .andExpect(content().string(jsonBasicVo))
                .andDo(print());

    }

    @Test
    @Transactional
    public void EVENT_TYPE_LIST_TEST() throws Exception {
        //given
        List<EventType> eventTypes = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            EventType eventType = new EventType("OPO" + i, "/test/test.jpg");

            eventTypes.add(eventType);

            eventTypeRepository.save(eventType);
        }

        String jsonEventTypeListVo = objectMapper.writeValueAsString(new EventTypeListVo(eventTypes));

        this.mockMvc.perform(get("/api/eventtypelist"))
                .andExpect(status().isOk())
                .andExpect(content().string(jsonEventTypeListVo))
                .andDo(print());
    }
}