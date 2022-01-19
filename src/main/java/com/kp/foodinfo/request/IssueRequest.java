package com.kp.foodinfo.request;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class IssueRequest {
    private String title;
    private String content;
    private String dateStr;
    private long brand_id;

    public IssueRequest(String title, String content, String dateStr, long brand_id) {
        this.title = title;
        this.content = content;
        this.dateStr = dateStr;
        this.brand_id = brand_id;
    }
}
