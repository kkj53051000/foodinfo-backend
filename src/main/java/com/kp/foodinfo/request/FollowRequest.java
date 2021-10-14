package com.kp.foodinfo.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class FollowRequest {
    private long brand_id;
    private long user_id;

    public FollowRequest(long brand_id, long user_id) {
        this.brand_id = brand_id;
        this.user_id = user_id;
    }
}
