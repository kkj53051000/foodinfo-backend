package com.kp.foodinfo.controller;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.util.StringToDateUtil;
import com.kp.foodinfo.vo.BrandVo;
import com.kp.foodinfo.request.BrandRequest;
import com.kp.foodinfo.service.BrandService;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.dto.BrandDto;
import com.kp.foodinfo.vo.BrandListVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class BrandController {

    private final BrandService brandService;

    @PostMapping("/admin/brandprocess")
    public BasicVo brandProcess(@RequestPart(name = "file", required = true) MultipartFile file, @RequestPart(name = "value", required = false) BrandRequest brandRequest) throws IOException {
        log.info("brandProcess() : in");
        BrandDto brandDto = new BrandDto(brandRequest.getName(), file, brandRequest.getFood_id());

        log.info("brandProcess() - BrandService - saveBrand() : run");
        brandService.saveBrand(brandDto);

        BasicVo basicVo = new BasicVo("success");

        log.info("brandProcess() : BasicVo return");
        return basicVo;
    }

    @GetMapping("/brand/{id}")
    public BrandVo brand(@PathVariable("id") long brand_id) {
        log.info("brand() : in");
        log.info("brand() - BrandService - getBrand() : run");

        BrandVo brandVo = brandService.getBrand(brand_id);

        return brandVo;
    }

    @GetMapping("/brandlist/{id}")
    public BrandListVo brandList(@PathVariable("id") long food_id) {
        log.info("brandList() : in");
        log.info("brandList() - BrandService - getBrandList() : run");
        List<Brand> brands = brandService.getBrandList(food_id);

        log.info("brandList() : BrandListVo return");
        return new BrandListVo(brands);
    }
}
