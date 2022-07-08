package com.kp.foodinfo.controller;

import com.kp.foodinfo.request.FoodItemReviewRemoveRequest;
import com.kp.foodinfo.request.FoodItemReviewRequest;
import com.kp.foodinfo.service.FoodItemReviewService;
import com.kp.foodinfo.vo.BasicVo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Basic;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FoodItemReviewController {
    private final FoodItemReviewService foodItemReviewService;

    @PostMapping("/fooditemreviewprocess")
    public BasicVo foodItemReviewProcess(@RequestBody FoodItemReviewRequest foodItemReviewRequest) {
        return foodItemReviewService.saveFoodItemReview(foodItemReviewRequest);
    }

    @PostMapping("/fooditemreviewremove")
    public BasicVo foodItemReviewRemove(@RequestBody FoodItemReviewRemoveRequest foodItemReviewRemoveRequest) {
        return foodItemReviewService.deleteFoodItemReview(foodItemReviewRemoveRequest);
    }
}
