package com.kp.foodinfo.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class FollowDto {
    private long user_id;
    private long brand_id;

    public FollowDto(long user_id, long brand_id) {
        this.user_id = user_id;
        this.brand_id = brand_id;
    }
}
