package com.kp.foodinfo.controller;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.service.BrandService;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.dto.BrandDto;
import com.kp.foodinfo.vo.BrandListVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PostMapping("/brandprocess")
    public BasicVo brandUploadProcess(@RequestParam("file") MultipartFile file, @RequestParam String name, HttpServletRequest request) {

        BrandDto brandDto = new BrandDto(name, file);

        String realPath = request.getServletContext().getRealPath("");

        brandService.saveBrand(brandDto, realPath);

        BasicVo basicVo = new BasicVo("success");

        return basicVo;
    }

    @PostMapping("/brandlist")
    public BrandListVo brandList() {
        List<Brand> brands = brandService.getBrandList();

        return new BrandListVo(brands);
    }
}
