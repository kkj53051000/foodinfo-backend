package com.kp.foodinfo.controller;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.request.BrandRequest;
import com.kp.foodinfo.service.BrandService;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.dto.BrandDto;
import com.kp.foodinfo.vo.BrandListVo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BrandController {

    private final BrandService brandService;

    @PostMapping("/admin/brandprocess")
    public BasicVo brandProcess(MultipartFile file, BrandRequest brandRequest) throws IOException {

        BrandDto brandDto = new BrandDto(brandRequest.getName(), file, brandRequest.getFood_id());

        brandService.saveBrand(brandDto);

        BasicVo basicVo = new BasicVo("success");

        return basicVo;
    }

    @PostMapping("/brandlist/{id}")
    public BrandListVo brandList(@PathVariable("id") long food_id) {
        List<Brand> brands = brandService.getBrandList(food_id);

        return new BrandListVo(brands);
    }
}
