package com.kp.foodinfo.controller;

import com.kp.foodinfo.service.EventTypeService;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.EventTypeListVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EventTypeController {
    private final EventTypeService eventTypeService;

    @PostMapping("/admin/eventtypeprocess")
    public BasicVo eventTypeProcess(MultipartFile file, String name, HttpServletRequest request) {
        String realPath = request.getServletContext().getRealPath("");

        eventTypeService.saveEventType(file, name, realPath);

        return new BasicVo("success");
    }

    @PostMapping("/admin/eventlist")
    public EventTypeListVo eventTypeList() {
        return eventTypeService.getEventTypeList();
    }
}
