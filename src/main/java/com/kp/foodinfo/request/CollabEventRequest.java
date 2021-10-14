package com.kp.foodinfo.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor
public class CollabEventRequest {
    private String title;
    private String content;
    private Date startDate;
    private Date endDate;
    private long brand_id;
    private long collabPlatform_id;

    public CollabEventRequest(String title, String content, Date startDate, Date endDate, long brand_id, long collabPlatform_id) {
        this.title = title;
        this. content = content;
        this.startDate = startDate;
        this.endDate = endDate;
        this.brand_id = brand_id;
        this.collabPlatform_id = collabPlatform_id;
    }
}
