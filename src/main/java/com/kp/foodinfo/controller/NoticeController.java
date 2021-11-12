package com.kp.foodinfo.controller;

import com.kp.foodinfo.request.NoticeRequest;
import com.kp.foodinfo.service.NoticeService;
import com.kp.foodinfo.vo.BasicVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NoticeController {
    private final NoticeService noticeService;

    @PostMapping("/admin/noticeprocess")
    public BasicVo noticeProcess(NoticeRequest noticeRequest, long user_id) {
        noticeService.saveNotice(noticeRequest, user_id);

        return new BasicVo("success");
    }
}
