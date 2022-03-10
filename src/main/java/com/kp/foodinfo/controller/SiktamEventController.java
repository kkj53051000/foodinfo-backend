package com.kp.foodinfo.controller;

import com.kp.foodinfo.argumentresolver.Login;
import com.kp.foodinfo.domain.SiktamEvent;
import com.kp.foodinfo.domain.UserSession;
import com.kp.foodinfo.request.SiktamEventRequest;
import com.kp.foodinfo.service.SiktamEventService;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.SiktamEventListVo;
import com.kp.foodinfo.vo.SiktamEventVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.RequestPartMethodArgumentResolver;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SiktamEventController {
    private final SiktamEventService siktamEventService;

    @PostMapping("/admin/siktameventprocess")
    public BasicVo siktamEventProcess(@RequestPart(name = "file", required = true) MultipartFile file, @RequestPart(name = "value", required = false)SiktamEventRequest siktamEventRequest) throws IOException {
        BasicVo basicVo = siktamEventService.saveSiktamEvent(file, siktamEventRequest);

        return basicVo;
    }

    @GetMapping("/siktameventlist")
    public SiktamEventListVo siktamEventList() {
        SiktamEventListVo siktamEventListVo = siktamEventService.getSiktamEventList();

        return siktamEventListVo;
    }

    @GetMapping("/siktamevent/{id}")
    public SiktamEventVo siktamEvent(@PathVariable("id") long siktamevent_id) {
        SiktamEventVo siktamEventVo = siktamEventService.getSiktamEvent(siktamevent_id);

        return siktamEventVo;
    }
}