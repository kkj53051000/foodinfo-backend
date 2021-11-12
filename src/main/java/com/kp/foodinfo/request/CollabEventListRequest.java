package com.kp.foodinfo.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class CollabEventListRequest {
    private long brand_id;
    private long collabPlatform_id;

    public CollabEventListRequest(long brand_id, long collabPlatform_id) {
        this.brand_id = brand_id;
        this.collabPlatform_id = collabPlatform_id;
    }
}
