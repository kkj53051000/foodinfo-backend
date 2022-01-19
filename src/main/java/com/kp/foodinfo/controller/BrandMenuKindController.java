package com.kp.foodinfo.controller;

import com.kp.foodinfo.domain.BrandMenuKind;
import com.kp.foodinfo.request.BrandMenuKindRequest;
import com.kp.foodinfo.service.BrandMenuKindService;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.BrandMenuKindListVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class BrandMenuKindController {
    private final BrandMenuKindService brandMenuKindService;

    @PostMapping("/admin/brandmenukindprocess")
    public BasicVo brandMenuKindUploadProcess(@RequestBody BrandMenuKindRequest brandMenuKindRequest) {
        log.info("brandMenuKindUploadProcess() - in");
        log.info("brandMenuKindUploadProcess() - BrandMenuKindService - saveBrandMenuKind() run");
        brandMenuKindService.saveBrandMenuKind(brandMenuKindRequest);

        BasicVo basicVo = new BasicVo("success");

        log.info("brandMenuKindUploadProcess() - BasicVo return");
        return basicVo;
    }

    @GetMapping("/brandmenukindlist/{id}")
    public BrandMenuKindListVo brandMenuKindList(@PathVariable("id") long brand_id) {
        log.info("brandMenuKindList() - in");
        log.info("brandMenuKindList() - BrandMenuKindService - getBrandMenuKinds() run");
        List<BrandMenuKind> brandMenuKinds = brandMenuKindService.getBrandMenuKinds(brand_id);

        log.info("brandMenuKindList() - BrandMenuKindListVo return");
        return new BrandMenuKindListVo(brandMenuKinds);
    }
}
