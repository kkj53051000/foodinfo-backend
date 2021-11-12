package com.kp.foodinfo.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class FollowRequest {
    private long user_id;
    private long brand_id;

    public FollowRequest(long user_id, long brand_id) {
        this.user_id = user_id;
        this.brand_id = brand_id;
    }
}
