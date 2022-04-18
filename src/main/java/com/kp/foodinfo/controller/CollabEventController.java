package com.kp.foodinfo.controller;

import com.kp.foodinfo.domain.CollabEvent;
import com.kp.foodinfo.request.CollabEventRequest;
import com.kp.foodinfo.request.CollabEventListRequest;
import com.kp.foodinfo.service.CollabEventService;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.CollabEventInfoListVo;
import com.kp.foodinfo.vo.CollabEventInfoVo;
import com.kp.foodinfo.vo.CollabEventListVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CollabEventController {
    private final CollabEventService collabEventService;

    @PostMapping("/admin/collabeventprocess")
    public BasicVo collabEventProcess(@RequestParam("file") MultipartFile file, CollabEventRequest collabEventRequest, HttpServletRequest request) {
        String realPath = request.getServletContext().getRealPath("");

        collabEventService.saveCollabEvent(file, collabEventRequest, realPath);

        BasicVo basicVo = new BasicVo("success");

        return basicVo;
    }

    // 콜라보 플랫폼 종류 및 갯수
    // (보류)
    @PostMapping("/collabeventmenulist")
    public CollabEventInfoListVo collabEventMenu(long brand_id) {
        List<CollabEventInfoVo> collabEventInfoVos = collabEventService.getCollabEventInfo(brand_id);

        return new CollabEventInfoListVo(collabEventInfoVos);
    }

    // 콜라보 이벤트 리스트 (플랫폼 별)
    // (보류)
    @PostMapping("/collabeventlist")
    public CollabEventListVo collabEventList(CollabEventListRequest collabEventListRequest) {
        List<CollabEvent> collabEvents = collabEventService.getCollabEventList(collabEventListRequest);

        return new CollabEventListVo(collabEvents);
    }
}
