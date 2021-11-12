package com.kp.foodinfo.controller;

import com.kp.foodinfo.request.BrandMenuKindRequest;
import com.kp.foodinfo.service.BrandMenuKindService;
import com.kp.foodinfo.vo.BasicVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BrandMenuKindController {
    private final BrandMenuKindService brandMenuKindService;

    @PostMapping("/admin/brandmenukindprocess")
    public BasicVo brandMenuKindUploadProcess(BrandMenuKindRequest brandMenuKindRequest) {

        brandMenuKindService.saveBrandMenuKind(brandMenuKindRequest);

        BasicVo basicVo = new BasicVo("success");

        return basicVo;
    }
}
