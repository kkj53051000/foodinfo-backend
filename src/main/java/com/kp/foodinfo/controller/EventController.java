package com.kp.foodinfo.controller;

import com.kp.foodinfo.request.BrandEventRequest;
import com.kp.foodinfo.request.EventRequest;
import com.kp.foodinfo.service.EventService;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.EventListVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EventController {
    private final EventService eventService;

    @PostMapping("/admin/eventprocess")
    public BasicVo eventProcess(MultipartFile file, EventRequest eventRequest, HttpServletRequest request) {

        String realPath = request.getServletContext().getRealPath("");

        eventService.saveEvent(file, eventRequest, realPath);

        BasicVo basicVo = new BasicVo("success");

        return basicVo;
    }

    @PostMapping("/eventlist")
    public EventListVo eventList(long brand_id) {

        return eventService.getEventList(brand_id);
    }
}
