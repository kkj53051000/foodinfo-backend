package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.BrandEvent;
import com.kp.foodinfo.domain.Event;
import com.kp.foodinfo.domain.EventType;
import com.kp.foodinfo.dto.FileTestUtilDto;
import com.kp.foodinfo.repository.BrandRepository;
import com.kp.foodinfo.repository.EventTypeRepository;
import com.kp.foodinfo.util.FileTestUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventTypeServiceTest {

    @Autowired
    EventTypeService eventTypeService;

    @Autowired
    EventTypeRepository eventTypeRepository;

    FileTestUtilDto fileRequest = FileTestUtil.getTestMultifile();

    @Test
    @Transactional
    void EVENT_TYPE_SAVE_TEST() {
        //given
        String name = "test";

        FileService fileService = Mockito.mock(FileService.class);
        eventTypeService = new EventTypeService(eventTypeRepository, fileService);

        //when
        eventTypeService.saveEventType(fileRequest.getMultipartFile(), name, fileRequest.getRealPath());

        //then
        Assertions.assertNotNull(eventTypeRepository.findByName(name).get());
    }

    @Test
    @Transactional
    void EVENT_TYPE_GET_LIST_TEST() {
        //given
        for (int i = 0; i < 10; i++) {
            String name = "OPO" + i;

            FileService fileService = Mockito.mock(FileService.class);
            eventTypeService = new EventTypeService(eventTypeRepository, fileService);

            //when
            eventTypeService.saveEventType(fileRequest.getMultipartFile(), name, fileRequest.getRealPath());

            //then
            Assertions.assertNotNull(eventTypeRepository.findByName(name).get());
        }
    }
}