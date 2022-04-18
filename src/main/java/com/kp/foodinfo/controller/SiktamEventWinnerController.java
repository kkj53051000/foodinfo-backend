package com.kp.foodinfo.controller;

import com.kp.foodinfo.request.SiktamEventWinnerRequest;
import com.kp.foodinfo.service.SiktamEventWinnerService;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.SiktamEventWinnerListVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SiktamEventWinnerController {
    private final SiktamEventWinnerService siktamEventWinnerService;

    // 이벤트 당첨자 선발
    @PostMapping("/admin/siktameventwinner/{id}")
    public BasicVo siktamEventWinner(@PathVariable("id") long siktamEvent_id) {
        BasicVo basicVo = siktamEventWinnerService.setSiktamEventWinner(siktamEvent_id);

        return basicVo;
    }

    @GetMapping("/admin/siktameventwinners/{id}")
    public SiktamEventWinnerListVo siktamEventWinnerList(@PathVariable("id") long siktamEvent_id) {
        SiktamEventWinnerListVo siktamEventWinnerListVo = siktamEventWinnerService.getSiktamEventWinnerList(siktamEvent_id);

        return siktamEventWinnerListVo;
    }
}
