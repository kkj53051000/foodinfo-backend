package com.kp.foodinfo.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class BrandMenuKindRequest {
    private String name;
    private int priority;
    private long brand_id;

    public BrandMenuKindRequest(String name, int priority, long brand_id) {
        this.name = name;
        this.priority = priority;
        this.brand_id = brand_id;
    }
}
