package com.kp.foodinfo.vo;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MenuSizeDeleteVo {
    private long id;
    private String menuName;
    private String menuPrice;
}
