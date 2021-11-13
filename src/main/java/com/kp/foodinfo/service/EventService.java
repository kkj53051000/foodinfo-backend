package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.Event;
import com.kp.foodinfo.domain.EventType;
import com.kp.foodinfo.repository.BrandRepository;
import com.kp.foodinfo.repository.EventRepository;
import com.kp.foodinfo.repository.EventTypeRepository;
import com.kp.foodinfo.request.EventRequest;
import com.kp.foodinfo.util.StringToDateUtil;
import com.kp.foodinfo.vo.EventListVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    private final FileService fileService;

    private final BrandRepository brandRepository;

    private final EventTypeRepository eventTypeRepository;

    public void saveEvent(MultipartFile file, EventRequest eventRequest) throws IOException {

        String clientPath = fileService.s3UploadProcess(file);

        Brand brand = brandRepository.findById(eventRequest.getBrand_id()).get();
        EventType eventType = eventTypeRepository.findById(eventRequest.getEventtype_id()).get();

        Date startDate = StringToDateUtil.stringToDateProcess(eventRequest.getStartDateStr() + " " + eventRequest.getStartTimeStr());
        Date endDate = StringToDateUtil.stringToDateProcess(eventRequest.getEndDateStr() + " " + eventRequest.getEndTimeStr());

        Event event = Event.builder()
                .title(eventRequest.getTitle())
                .content(eventRequest.getContent())
                .img(clientPath)
                .startDate(startDate)
                .endDate(endDate)
                .brand(brand)
                .eventType(eventType)
                .build();

        eventRepository.save(event);
    }

    public EventListVo getEventList(long brand_id) {
        List<Event> events = eventRepository.findAll();

        return new EventListVo(events);
    }
}
