package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.EventType;
import com.kp.foodinfo.repository.EventTypeRepository;
import com.kp.foodinfo.vo.EventTypeListVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EventTypeService {
    private final EventTypeRepository eventTypeRepository;

    private final FileService fileService;

    public void saveEventType(MultipartFile file, String name) throws IOException {
        String clientPath = fileService.s3UploadProcess(file);

        EventType eventType = new EventType(name, clientPath);

        eventTypeRepository.save(eventType);
    }

    public EventTypeListVo getEventTypeList() {
        List<EventType> eventTypes = eventTypeRepository.findAll();

        return new EventTypeListVo(eventTypes);
    }
}
