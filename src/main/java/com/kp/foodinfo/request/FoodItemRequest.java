package com.kp.foodinfo.request;

import lombok.Getter;

@Getter
public class FoodItemRequest {
    private String name;
    private Integer price;
    private String date;
    private Long foodBrandId;
    private Long foodNormalCategoryId;
    private Long foodSpecialCategoryId;
}
