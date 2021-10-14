package com.kp.foodinfo.controller;

import com.kp.foodinfo.request.BrandMenuKindRequest;
import com.kp.foodinfo.service.BrandMenuKindService;
import com.kp.foodinfo.vo.BasicVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BrandMenuKindController {
    private final BrandMenuKindService brandMenuKindService;

    @PostMapping("/brandmenukindprocess")
    public BasicVo brandMenuKindUploadProcess(BrandMenuKindRequest brandMenuKindRequest) {
        brandMenuKindService.saveBrandMenuKind(brandMenuKindRequest);

        BasicVo basicVo = new BasicVo("success");

        return basicVo;
    }
}
