package com.kp.foodinfo.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PriceGameVo {
    private long id;
    private long brandId;
    private String brandName;
    private String brandImg;
    private String menuName;
    private String menuTitle;
    private String menuImg;
    private String menuPrice;
    private String menuSize;
}
