package com.kp.foodinfo.controller;

import com.kp.foodinfo.request.BrandEventRequest;
import com.kp.foodinfo.request.EventRequest;
import com.kp.foodinfo.service.EventService;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.EventListVo;
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
public class EventController {
    private final EventService eventService;

    @PostMapping("/admin/eventprocess")
    public BasicVo eventProcess(@RequestPart(name = "file", required = true) MultipartFile file, @RequestPart(name = "value", required = false) EventRequest eventRequest) throws IOException {
        log.info("eventProcess() : in");
        log.info("eventProcess() - EventService - saveEvent() : run");
        eventService.saveEvent(file, eventRequest);

        BasicVo basicVo = new BasicVo("success");

        log.info("eventProcess() : BasicVo return");
        return basicVo;
    }

    @GetMapping("/eventlist/{id}")
    public EventListVo eventList(@PathVariable("id") long brand_id) {
        log.info("eventList() : in");
        log.info("eventList() - EventService - getEventList() : run");
        log.info("eventList() : EventListVo return");
        return eventService.getEventList(brand_id);
    }
}
