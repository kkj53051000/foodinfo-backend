package com.kp.foodinfo.vo;

import com.kp.foodinfo.domain.Food;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class FoodVo {
    private String name;
    private String img;

    public FoodVo(String name, String img) {
        this.name = name;
        this.img = img;
    }
}
