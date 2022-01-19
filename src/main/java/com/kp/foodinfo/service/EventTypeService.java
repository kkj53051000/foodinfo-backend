package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.EventType;
import com.kp.foodinfo.repository.EventTypeRepository;
import com.kp.foodinfo.vo.EventTypeListVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class EventTypeService {
    private final EventTypeRepository eventTypeRepository;

    private final FileService fileService;

    public void saveEventType(MultipartFile file, String name) throws IOException {
        log.info("saveEventType() : in");
        log.info("saveEventType() - FileService - s3UploadProcess() : run");
        String clientPath = fileService.s3UploadProcess(file);

        EventType eventType = new EventType(name, clientPath);

        log.info("saveEventType() - EventTypeRepository - save() : run");
        eventTypeRepository.save(eventType);
    }

    public EventTypeListVo getEventTypeList() {
        log.info("getEventTypeList() : in");
        log.info("getEventTypeList() - EventTypeRepository - findAll() : run");
        List<EventType> eventTypes = eventTypeRepository.findAll();

        log.info("getEventTypeList() - EventTypeListVo return");
        return new EventTypeListVo(eventTypes);
    }
}
