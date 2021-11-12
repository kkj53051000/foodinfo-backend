package com.kp.foodinfo.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class NoticeRequest {
    private String title;
    private String content;

    public NoticeRequest(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
