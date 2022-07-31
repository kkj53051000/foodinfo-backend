package com.kp.foodinfo.controller;

import com.kp.foodinfo.request.FoodBrandModifyRequest;
import com.kp.foodinfo.request.FoodBrandRemoveRequest;
import com.kp.foodinfo.request.FoodBrandRequest;
import com.kp.foodinfo.service.FoodBrandService;
import com.kp.foodinfo.vo.BasicVo;
import com.kp.foodinfo.vo.FoodBrandAllListVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FoodBrandController {
    private final FoodBrandService foodBrandService;

    @PostMapping("/admin/foodbrandprocess")
    public BasicVo foodBrandProcess(
            @RequestPart(name = "file") MultipartFile file,
            @RequestPart(name = "value") FoodBrandRequest foodBrandRequest) {
        return foodBrandService.saveFoodBrand(file, foodBrandRequest);
    }

    @PostMapping("/admin/foodbrandmodify")
    public BasicVo foodBrandModify(
            @RequestPart(name = "file", required = false) MultipartFile file,
            @RequestPart(name = "value") FoodBrandModifyRequest foodBrandModifyRequest) {
        return foodBrandService.alertFoodBrand(file, foodBrandModifyRequest);
    }

    @PostMapping("/admin/foodbrandremove")
    public BasicVo foodBrandRemove(@RequestBody FoodBrandRemoveRequest foodBrandRemoveRequest) {
        return foodBrandService.deleteFoodBrand(foodBrandRemoveRequest);
    }

    @GetMapping("/foodbrandalllist")
    public FoodBrandAllListVo foodBrandAllList() {

        return foodBrandService.selectFoodBrandAllList();
    }
}
