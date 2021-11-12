package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.EventType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventTypeRepositoryTest {
    @Autowired
    EventTypeRepository eventTypeRepository;

    @Test
    @Transactional
    void EVENT_TYPE_INSERT_TEST() {
        //given
        EventType eventType = new EventType("OTO", "/test/test.jpg");

        //when
        eventTypeRepository.save(eventType);

        //then
        Assertions.assertEquals(eventType, eventTypeRepository.findById(eventType.getId()).get());
    }

    @Test
    @Transactional
    void EVENT_TYPE_LIST_SELECT_TEST() {
        //given
        List<EventType> eventTypes = new ArrayList<>();

        for (int i = 0 ; i < 10; i++) {
            EventType eventType = new EventType("OTO" + i, "/test/test.jpg");

            eventTypes.add(eventType);

            //when
            eventTypeRepository.save(eventType);
        }

        //then
        Assertions.assertEquals(eventTypes, eventTypeRepository.findAll());
    }
}