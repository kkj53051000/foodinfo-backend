package com.kp.foodinfo.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BrandRequest {
    private String name;
    private long food_id;

    public BrandRequest(String name, long food_id) {
        this.name = name;
        this.food_id = food_id;
    }
}
