package com.kp.foodinfo.controller;

import com.kp.foodinfo.request.FoodSpecialCategoryRequest;
import com.kp.foodinfo.service.FoodSpecialCategoryService;
import com.kp.foodinfo.vo.BasicVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FoodSpecialCategoryController {
    private final FoodSpecialCategoryService foodSpecialCategoryService;

    @PostMapping("/admin/foodspecialcategoryprocess")
    public BasicVo foodSpecialCategoryProcess(
            @RequestPart(name = "file") MultipartFile file,
            @RequestPart(name = "value") FoodSpecialCategoryRequest foodSpecialCategoryRequest) {
        return foodSpecialCategoryService.saveFoodSpecialCategory(file, foodSpecialCategoryRequest);
    }
}
