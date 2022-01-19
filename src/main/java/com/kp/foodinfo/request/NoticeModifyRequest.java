package com.kp.foodinfo.request;

import lombok.Getter;

@Getter
public class NoticeModifyRequest {
    private long notice_id;
    private String title;
    private String content;
}
