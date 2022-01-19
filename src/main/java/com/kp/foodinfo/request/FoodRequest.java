package com.kp.foodinfo.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FoodRequest {
    private String name;

    public FoodRequest(String name) {
        this.name = name;
    }
}
