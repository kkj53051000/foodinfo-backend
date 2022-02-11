package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.Event;
import com.kp.foodinfo.domain.EventType;
import com.kp.foodinfo.repository.BrandRepository;
import com.kp.foodinfo.repository.EventRepository;
import com.kp.foodinfo.repository.EventTypeRepository;
import com.kp.foodinfo.request.EventRequest;
import com.kp.foodinfo.util.ReturnStatus;
import com.kp.foodinfo.util.StringToDateUtil;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.EventListVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EventService {
    private final EventRepository eventRepository;

    private final FileService fileService;

    private final BrandRepository brandRepository;

    private final EventTypeRepository eventTypeRepository;

    public void saveEvent(MultipartFile file, EventRequest eventRequest) throws IOException {
        log.info("saveEvent() : in");
        log.info("saveEvent() - FileService - s3UploadProcess() : run");
        String clientPath = fileService.s3UploadProcess(file);

        log.info("saveEvent() - BrandRepository - findById() : run");
        Brand brand = brandRepository.findById(eventRequest.getBrand_id()).get();
        log.info("saveEvent() - EventTypeRepository - findById() : run");
        EventType eventType = eventTypeRepository.findById(eventRequest.getEventtype_id()).get();

        Date startDate = StringToDateUtil.stringToDateProcess(eventRequest.getStartDateStr());
        Date endDate = StringToDateUtil.stringToDateProcess(eventRequest.getEndDateStr());

        Event event = Event.builder()
                .title(eventRequest.getTitle())
                .content(eventRequest.getContent())
                .img(clientPath)
                .startDate(startDate)
                .endDate(endDate)
                .brand(brand)
                .eventType(eventType)
                .build();

        log.info("saveEvent() - EventRepository - save()");
        eventRepository.save(event);

        //Brand RecentlyUpdate 최신화
        brand.setRecentlyUpdate(startDate);
        brandRepository.save(brand);
    }

    public EventListVo getEventList(long brand_id) {
        log.info("getEventList() : in");
        log.info("getEventList() - BrandRepository - findById() : run");
        Brand brand = brandRepository.findById(brand_id).get();

        log.info("getEventList() - EventRepository - findByBrand() : run");
        List<Event> events = eventRepository.findByBrand(brand);

        log.info("getEventList() : EventListVo return");
        return new EventListVo(events);
    }

    public BasicVo deleteEvent(long event_id) {
        Event event = eventRepository.findById(event_id).get();

        eventRepository.delete(event);

        return new BasicVo(ReturnStatus.success);
    }
}
