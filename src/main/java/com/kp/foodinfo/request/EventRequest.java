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
