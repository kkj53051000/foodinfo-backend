package com.kp.foodinfo.controller;

import com.kp.foodinfo.domain.BrandEvent;
import com.kp.foodinfo.request.BrandEventRequest;
import com.kp.foodinfo.service.BrandEventService;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.BrandEventListVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BrandEventController {
    private final BrandEventService brandEventService;

    @PostMapping("/admin/brandeventprocess")
    public BasicVo brandEventUploadProcess(MultipartFile file, BrandEventRequest brandEventRequest, HttpServletRequest request) {
        String realPath = request.getServletContext().getRealPath("");

        brandEventService.saveBrandEvent(file, brandEventRequest, realPath);

        BasicVo basicVo = new BasicVo("success");

        return basicVo;
    }

    @PostMapping("/brandeventlist")
    public BrandEventListVo getBrandEvents(long brand_id) {
        List<BrandEvent> brandEvents = brandEventService.getBrandEvents(brand_id);

        return new BrandEventListVo(brandEvents);
    }
}
