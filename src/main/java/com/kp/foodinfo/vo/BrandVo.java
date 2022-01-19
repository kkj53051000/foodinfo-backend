package com.kp.foodinfo.vo;

import com.kp.foodinfo.domain.Brand;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BrandVo {
    private long id;
    private String name;
    private String img;

    public BrandVo(Brand brand) {
        this.id = brand.getId();
        this.name = brand.getName();
        this.img = brand.getImg();
    }
}
