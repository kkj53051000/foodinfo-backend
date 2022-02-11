package com.kp.foodinfo.dto;

import lombok.Data;

@Data
public class ExcelMenuDto {

    private int id;
    private String name;
    private String menuType;
    private String size;
    private int price;
    private String img;
}
