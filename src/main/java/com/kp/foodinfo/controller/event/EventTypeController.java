package com.kp.foodinfo.controller;

import com.kp.foodinfo.request.EventTypeRequest;
import com.kp.foodinfo.service.EventTypeService;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.EventTypeListVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class EventTypeController {
    private final EventTypeService eventTypeService;

    @PostMapping("/admin/eventtypeprocess")
    public BasicVo eventTypeProcess(@RequestPart(name = "file", required = true) MultipartFile file, @RequestPart(name = "value", required = false) EventTypeRequest eventTypeRequest) throws IOException {
        eventTypeService.saveEventType(file, eventTypeRequest.getName());

        return new BasicVo("success");
    }

    @GetMapping("/eventtypelist")
    public EventTypeListVo eventTypeList() {

        return eventTypeService.getEventTypeList();
    }
}
