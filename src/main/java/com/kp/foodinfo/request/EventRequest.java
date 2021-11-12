package com.kp.foodinfo.request;

import com.kp.foodinfo.util.StringToDateUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.lang.annotation.Before;

import javax.annotation.PostConstruct;
import java.util.Date;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Builder
public class EventRequest {
    private String title;
    private String content;
    private String startDateStr;
    private String startTimeStr;
    private String endDateStr;
    private String endTimeStr;
    private long brand_id;
    private long eventtype_id;

    @PostConstruct
    void setUp() {

    }

    public EventRequest(String title, String content, String startDateStr, String startTimeStr, String endDateStr, String endTimeStr, long brand_id, long eventtype_id) {
        this.title = title;
        this.content = content;
        this.startDateStr = startDateStr;
        this.startTimeStr = startTimeStr;
        this.endDateStr = endDateStr;
        this.endTimeStr = endTimeStr;
        this.brand_id = brand_id;
        this.eventtype_id = eventtype_id;

        //startDate =
    }
}
