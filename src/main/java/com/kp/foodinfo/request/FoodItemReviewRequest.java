package com.kp.foodinfo.request;

import lombok.Getter;

@Getter
public class FoodItemReviewRequest {
    private String reviewContent;
    private Byte rePurchase;
    private Long foodItemId;
}
