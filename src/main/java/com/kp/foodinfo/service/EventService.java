package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.Event;
import com.kp.foodinfo.domain.EventType;
import com.kp.foodinfo.repository.BrandRepository;
import com.kp.foodinfo.repository.EventRepository;
import com.kp.foodinfo.repository.EventTypeRepository;
import com.kp.foodinfo.request.EventRequest;
import com.kp.foodinfo.util.ReturnStatus;
import com.kp.foodinfo.util.DateFormatUtil;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.EventListVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
        String clientPath = fileService.s3UploadProcess(file);

        Brand brand = brandRepository.findById(eventRequest.getBrand_id()).get();
        EventType eventType = eventTypeRepository.findById(eventRequest.getEventtype_id()).get();

        Date startDate = DateFormatUtil.stringToDateDayProcess(eventRequest.getStartDateStr());
        Date endDate = DateFormatUtil.stringToDateDayProcess(eventRequest.getEndDateStr());

        Event event = Event.builder()
                .title(eventRequest.getTitle())
                .content(eventRequest.getContent())
                .img(clientPath)
                .startDate(startDate)
                .endDate(endDate)
                .startDateInt(DateFormatUtil.stringToIntegerProcess(eventRequest.getStartDateStr()))
                .endDateInt(DateFormatUtil.stringToIntegerProcess(eventRequest.getEndDateStr()))
                .brand(brand)
                .eventType(eventType)
                .build();


        eventRepository.save(event);

        //Brand RecentlyUpdate 최신화
        brand.setRecentlyUpdate(startDate);
        brandRepository.save(brand);
    }

    public EventListVo getEventList(long brand_id) {
        Brand brand = brandRepository.findById(brand_id).get();

        List<Event> events = eventRepository.findByBrand(brand);

        List<Event> availableEvents = new ArrayList<>();

        Date now = DateFormatUtil.dateToDateProcess(new Date());
        int nowInt = DateFormatUtil.dateToIntegerProcess(now);

        for (Event event : events){
            if(nowInt <= DateFormatUtil.dateToIntegerProcess(event.getEndDate())){
                availableEvents.add(event);
            }else {
                continue;
            }
        }

        Collections.reverse(availableEvents);


        return new EventListVo(availableEvents);
    }

    public BasicVo deleteEvent(long event_id) {
        Event event = eventRepository.findById(event_id).get();

        eventRepository.delete(event);

        return new BasicVo(ReturnStatus.success);
    }
}
