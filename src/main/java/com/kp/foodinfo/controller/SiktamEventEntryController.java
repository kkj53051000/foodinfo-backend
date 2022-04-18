package com.kp.foodinfo.controller;

import com.kp.foodinfo.argumentresolver.Login;
import com.kp.foodinfo.domain.UserSession;
import com.kp.foodinfo.request.SiktamEventEntryRequest;
import com.kp.foodinfo.service.SiktamEventEntryService;
import com.kp.foodinfo.vo.BasicVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SiktamEventEntryController {
    private final SiktamEventEntryService siktamEventEntryService;

    @PostMapping("/user/siktamevententry")
    public BasicVo siktamEventEntryProcess(@Login UserSession userSession, @RequestBody SiktamEventEntryRequest siktamEventEntryRequest) {
        BasicVo basicVo = siktamEventEntryService.saveSiktamEventEntry(userSession.getUserId(), siktamEventEntryRequest);


        return basicVo;
    }
}
