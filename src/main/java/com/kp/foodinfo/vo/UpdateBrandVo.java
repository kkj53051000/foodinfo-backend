package com.kp.foodinfo.vo;

import com.kp.foodinfo.domain.Brand;
import lombok.Data;

import java.util.Date;

@Data
public class UpdateBrandVo {
    String name;
    String img;
    Date date;

    public UpdateBrandVo(String name, String img, Date date) {
        this.name = name;
        this.img = img;
        this.date = date;
    }
}
