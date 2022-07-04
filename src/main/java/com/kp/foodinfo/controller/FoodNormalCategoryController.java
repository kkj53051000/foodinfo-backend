package com.kp.foodinfo.controller;

import com.kp.foodinfo.service.FoodNormalCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FoodNormalCategoryController {
    private final FoodNormalCategoryService foodNormalCategoryService;
}
