package com.kp.foodinfo.request;

import lombok.Getter;

@Getter
public class MenuSizeRequest {
    private Long menuId;
    private Long sizeId;
    private int price;
}
