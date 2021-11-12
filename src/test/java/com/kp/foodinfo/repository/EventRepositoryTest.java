package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.Event;
import com.kp.foodinfo.domain.EventType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@SpringBootTest
class EventRepositoryTest {
    @Autowired
    EventRepository eventRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    EventTypeRepository eventTypeRepository;

    @Test
    @Transactional
    void EVENT_INSERT_TEST() {
        //given
        Brand brand = new Brand("pizzaHut", "/test/test.jpg");
        brandRepository.save(brand);

        EventType eventType = new EventType("OPO", "/test/test.jpg");
        eventTypeRepository.save(eventType);

        Event event = Event.builder()
                .title("title")
                .content("content")
                .img("/test/test.jpg")
                .startDate(new Date())
                .endDate(new Date())
                .brand(brand)
                .eventType(eventType)
                .build();

        //when
        eventRepository.save(event);

        //then
        Assertions.assertEquals(event, eventRepository.findById(event.getId()).get());
    }

    @Test
    @Transactional
    void EVENT_SELECT_LIST_TEST() {
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

            //when
            eventRepository.save(event);
        }


        //then
        Assertions.assertEquals(events, eventRepository.findAll());
    }
}