package com.kp.foodinfo.controller;

import com.kp.foodinfo.domain.CollabEvent;
import com.kp.foodinfo.request.CollabEventRequest;
import com.kp.foodinfo.request.CollabEventListRequest;
import com.kp.foodinfo.service.CollabEventService;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.CollabEventMenuVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class CollabEventController {
    private final CollabEventService collabEventService;

    @PostMapping("/collabeventprocess")
    public BasicVo collabEventProcess(@RequestParam("file")MultipartFile file, @RequestParam CollabEventRequest collabEventRequest, HttpServletRequest request) {
        String realPath = request.getServletContext().getRealPath("");

        collabEventService.saveCollabEvent(file, collabEventRequest, realPath);

        BasicVo basicVo = new BasicVo("success");

        return basicVo;
    }

    // 콜라보 플랫폼 종류 및 갯수
    @PostMapping("/collabeventlist")
    public List<CollabEventMenuVo> collabEventMenu(long brand_id) {
        List<CollabEventMenuVo> collabEventMenuVos = collabEventService.getCollabEventMenu(brand_id);

        return collabEventMenuVos;
    }

    // 콜라보 이벤트 리스트 (플랫폼 별)
    public List<CollabEvent> collabEventList(CollabEventListRequest collabEventListRequest) {
        List<CollabEvent> collabEvents = collabEventService.getCollabEventList(collabEventListRequest);

        return collabEvents;
    }
}
