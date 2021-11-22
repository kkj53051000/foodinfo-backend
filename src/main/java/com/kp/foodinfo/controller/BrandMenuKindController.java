package com.kp.foodinfo.controller;

import com.kp.foodinfo.domain.BrandMenuKind;
import com.kp.foodinfo.request.BrandMenuKindRequest;
import com.kp.foodinfo.service.BrandMenuKindService;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.BrandMenuKindListVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/brandmenukindlist/{id}")
    public BrandMenuKindListVo brandMenuKindList(@PathVariable("id") long brand_id) {

        List<BrandMenuKind> brandMenuKinds = brandMenuKindService.getBrandMenuKinds(brand_id);


        return new BrandMenuKindListVo(brandMenuKinds);
    }
}
