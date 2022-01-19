package com.kp.foodinfo.vo;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.List;


@Builder
@Data
public class MenuVo {
    private long id;
    private String name;
    private String price;
    private List<MenuSizeVo> menuSizeVo;
    private String img;
}
