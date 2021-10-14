package com.kp.foodinfo.controller;

import com.kp.foodinfo.domain.BrandEvent;
import com.kp.foodinfo.request.BrandEventRequest;
import com.kp.foodinfo.service.BrandEventService;
import com.kp.foodinfo.vo.BasicVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BrandEventController {
    private final BrandEventService brandEventService;

    private String test = "DSf";

    @PostMapping("/brandeventprocess")
    public BasicVo brandEventUploadProcess(@RequestParam("file") MultipartFile file, @RequestParam BrandEventRequest brandEventRequest, HttpServletRequest request) {

        String realPath = request.getServletContext().getRealPath("");

        brandEventService.saveBrandEvent(file, brandEventRequest, realPath);

        BasicVo basicVo = new BasicVo("success");

        return basicVo;
    }

    @PostMapping("/brandeventlist")
    public List<BrandEvent> getBrandEvents(long brand_id) {
        List<BrandEvent> brandEvents = brandEventService.getBrandEvents(brand_id);

        return brandEvents;
    }
}
