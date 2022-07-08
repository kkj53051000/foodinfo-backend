package com.kp.foodinfo.repository;

import lombok.Getter;

@Getter
public class MenuReviewModifyRequest {
    private Long id;
    private String reviewContent;
    private Byte rePurchase;
    private Long menuId;
}
