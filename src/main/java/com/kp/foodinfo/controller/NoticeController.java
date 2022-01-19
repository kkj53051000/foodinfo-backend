package com.kp.foodinfo.controller;

import com.kp.foodinfo.request.NoticeModifyRequest;
import com.kp.foodinfo.request.NoticeRequest;
import com.kp.foodinfo.service.NoticeService;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.NoticeListVo;
import com.kp.foodinfo.vo.NoticeVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class NoticeController {
    private final NoticeService noticeService;

    // 공지사항 업로드
    @PostMapping("/admin/noticeprocess")
    public BasicVo noticeProcess(@RequestBody NoticeRequest noticeRequest) {

        System.out.println("NoticeRequest value : " + noticeRequest.getTitle() + noticeRequest.getContent());
        log.info("noticeProcess() : in");
        log.info("noticeProcess() - NoticeService - saveNotice() : run");
        noticeService.saveNotice(noticeRequest);

        log.info("noticeProcess() : BasicVo return");
        return new BasicVo("success");
    }

    // 공지사항 가져오기
    @GetMapping("/notice/{id}")
    public NoticeVo notice(@PathVariable("id") long notice_id) {
        NoticeVo noticeVo = noticeService.getNotice(notice_id);

        return noticeVo;
    }

    // 모든 공지사항 리스트
    @GetMapping("/noticelist")
    public NoticeListVo noticeList() {
        NoticeListVo noticeListVo = noticeService.getNoticeList();

        return noticeListVo;
    }

    // 공지사항 수정
    @PostMapping("/admin/updatenotice")
    public BasicVo noticeModify(@RequestBody NoticeModifyRequest noticeModifyRequest) {
        BasicVo basicVo = noticeService.updateNotice(noticeModifyRequest);

        return basicVo;
    }
}
