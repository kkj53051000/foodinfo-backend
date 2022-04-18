package com.kp.foodinfo.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CollabEventRequest {
    private String title;
    private String content;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private long brand_id;
    private long collabPlatform_id;

    public CollabEventRequest(String title, String content, String startDate, String startTime, String endDate, String endTime, long brand_id, long collabPlatform_id) {
        this.title = title;
        this.content = content;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.brand_id = brand_id;
        this.collabPlatform_id = collabPlatform_id;
    }
}
