package com.kp.foodinfo.repository;

import lombok.Getter;

@Getter
public class MenuReviewRequest {
    private String reviewContent;
    private Byte rePurchase;
    private Long menuId;
}
