package com.kp.foodinfo.service;

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
import com.kp.foodinfo.util.FileTestUtil;
import com.kp.foodinfo.vo.EventListVo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EventServiceTest {
    @Autowired
    EventService eventService;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    EventTypeRepository eventTypeRepository;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    FoodRepository foodRepository;

    @Test
    @Transactional
    void EVENT_SAVE_TEST() throws IOException {
        //given
        FileTestUtilControllerDto fileRequest = FileTestUtil.getTestMultifileController();

        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        Brand brand = new Brand("pizzaHut", "/test/test.jpg", food);
        brandRepository.save(brand);

        EventType eventType = new EventType("OPO", "/test/test.jpg");
        eventTypeRepository.save(eventType);

        EventRequest eventRequest = new EventRequest("title", "content", "2021-10-10", "00:00", "2021-10-11", "00:00", brand.getId(), eventType.getId());

        FileService fileService = Mockito.mock(FileService.class);
        eventService = new EventService(eventRepository, fileService, brandRepository, eventTypeRepository);

        //when
        eventService.saveEvent(fileRequest.getFile(), eventRequest);

        //then
        Assertions.assertNotNull(eventRepository.findByTitle(eventRequest.getTitle()).get());

    }

    @Test
    @Transactional
    void EVENT_GET_LIST_TEST() {
        //given
        Food food = new Food("pizza", "/test/test.jpg");
        foodRepository.save(food);

        Brand brand = new Brand("pizzaHut", "/test/test.jpg", food);
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

        EventListVo eventListVo = new EventListVo(events);

        //then

        Assertions.assertEquals(eventListVo, eventService.getEventList(brand.getId()));
    }
}