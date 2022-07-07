package com.kp.foodinfo.controller;

import com.kp.foodinfo.request.FoodNormalCategoryRequest;
import com.kp.foodinfo.service.FoodNormalCategoryService;
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
public class FoodNormalCategoryController {
    private final FoodNormalCategoryService foodNormalCategoryService;

    @PostMapping
    public BasicVo foodNormalCategoryProcess(
            @RequestPart(name = "file") MultipartFile file,
            @RequestPart(name = "value") FoodNormalCategoryRequest foodNormalCategoryRequest) {

        return foodNormalCategoryService.saveFoodNormalCategory(file, foodNormalCategoryRequest);
    }
}
