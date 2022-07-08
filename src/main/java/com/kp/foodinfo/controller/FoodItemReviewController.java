package com.kp.foodinfo.controller;

import com.kp.foodinfo.request.FoodItemReviewRequest;
import com.kp.foodinfo.service.FoodItemReviewService;
import com.kp.foodinfo.vo.BasicVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FoodItemReviewController {
    private final FoodItemReviewService foodItemReviewService;

    @PostMapping("/fooditemreviewprocess")
    public BasicVo foodItemReviewProcess(@RequestBody FoodItemReviewRequest foodItemReviewRequest) {
        return foodItemReviewService.saveFoodItemReview(foodItemReviewRequest);
    }
}
