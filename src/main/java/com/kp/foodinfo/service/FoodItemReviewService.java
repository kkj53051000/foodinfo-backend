package com.kp.foodinfo.service;

import com.kp.foodinfo.domain.FoodItem;
import com.kp.foodinfo.domain.FoodItemReview;
import com.kp.foodinfo.repository.FoodItemRepository;
import com.kp.foodinfo.repository.FoodItemReviewRepository;
import com.kp.foodinfo.request.FoodItemReviewRemoveRequest;
import com.kp.foodinfo.request.FoodItemReviewRequest;
import com.kp.foodinfo.vo.BasicVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FoodItemReviewService {
    private final FoodItemReviewRepository foodItemReviewRepository;

    private final FoodItemRepository foodItemRepository;

    public BasicVo saveFoodItemReview(FoodItemReviewRequest foodItemReviewRequest) {

        FoodItem foodItem = foodItemRepository.findById(foodItemReviewRequest.getFoodItemId())
                .get();

        FoodItemReview foodItemReview = FoodItemReview.builder()
                .reviewContent(foodItemReviewRequest.getReviewContent())
                .rePurchase(foodItemReviewRequest.getRePurchase())
                .foodItem(foodItem)
                .deleteAt(false)
                .build();

        foodItemReviewRepository.save(foodItemReview);

        return new BasicVo("success");
    }

    public BasicVo deleteFoodItemReview(FoodItemReviewRemoveRequest foodItemReviewRemoveRequest) {
        FoodItem foodItem = foodItemRepository.findById(foodItemReviewRemoveRequest.getId())
                .get();

        foodItemRepository.delete(foodItem);

        return new BasicVo("success");
    }

}
