package com.kp.foodinfo.controller.brand;

import com.kp.foodinfo.aop.LogExcutionTime;
import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.vo.BrandVo;
import com.kp.foodinfo.request.BrandRequest;
import com.kp.foodinfo.service.BrandService;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.dto.BrandDto;
import com.kp.foodinfo.vo.BrandListVo;
import com.kp.foodinfo.vo.MenuVoList;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class BrandController {

    private final BrandService brandService;

    @Autowired
    RedisTemplate redisTemplate;

    @PostMapping("/admin/brandprocess")
    public BasicVo brandProcess(@RequestPart(name = "file", required = true) MultipartFile file, @RequestPart(name = "value", required = false) BrandRequest brandRequest) throws IOException {
        BrandDto brandDto = new BrandDto(brandRequest.getName(), file, brandRequest.getFood_id());

        brandService.saveBrand(brandDto);

        BasicVo basicVo = new BasicVo("success");

        return basicVo;
    }

    @GetMapping("/brand/{id}")
    public BrandVo brand(@PathVariable("id") long brand_id) {

        BrandVo brandVo = brandService.getBrand(brand_id);

        return brandVo;
    }
    
    @GetMapping("/brandlist/{id}")
    public BrandListVo brandList(@PathVariable("id") long food_id) {

        ValueOperations<String, BrandListVo> valueOperations = redisTemplate.opsForValue();

        String redisName = "BrandController.brandList()" + String.valueOf(food_id);

        BrandListVo brandListVo = valueOperations.get(redisName);

        if (brandListVo == null) {
            List<Brand> brands = brandService.getBrandList(food_id);
            brandListVo = new BrandListVo(brands);

            valueOperations.set(redisName, brandListVo, 20L, TimeUnit.MINUTES);
        }
        
        return brandListVo;
    }

    @GetMapping("/randombrandlist/{id}")
    public BrandListVo randomBrandList(@PathVariable("id") long food_id) {
        return brandService.getRandomBrandList(food_id);
    }

    @GetMapping("/abcbrandlist/{id}")
    public BrandListVo abcBrandList(@PathVariable("id") long food_id) {
        return brandService.getAbcBrandList(food_id);
    }
}
