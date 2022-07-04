package com.kp.foodinfo.controller;

import com.kp.foodinfo.service.FoodItemReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FoodItemReviewController {
    private final FoodItemReviewService foodItemReviewService;
}
