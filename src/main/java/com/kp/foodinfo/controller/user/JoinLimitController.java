package com.kp.foodinfo.controller;

import com.kp.foodinfo.service.JoinLimitService;
import com.kp.foodinfo.vo.BasicVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class JoinLimitController {

    private final JoinLimitService joinLimitService;

    //    @GetMapping("/joinlimitprocess")
    public BasicVo joinLimit(@RequestParam("joinlimit") int joinLimitNum) {
        return joinLimitService.saveJoinLimit(joinLimitNum);
    }

    @GetMapping("/joincheck")
    public BasicVo joinCheck() {
        return joinLimitService.joinCheck();
    }
}