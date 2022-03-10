package com.kp.foodinfo.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class EventRequest {
    private String title;
    private String content;
    private String startDateStr;
    private String endDateStr;
    private long brand_id;
    private long eventtype_id;


    public EventRequest(String title, String content, String startDateStr, String endDateStr, long brand_id, long eventtype_id) {
        this.title = title;
        this.content = content;
        this.startDateStr = startDateStr;
        this.endDateStr = endDateStr;
        this.brand_id = brand_id;
        this.eventtype_id = eventtype_id;
    }
}
