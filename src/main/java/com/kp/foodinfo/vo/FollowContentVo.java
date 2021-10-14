package com.kp.foodinfo.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor
public class FollowContentVo{
    private String brandName;
    private String brandImg;
    private String eventType;
    // 콜라보 이벤트만.
    private String collabPlatformName;
    private String eventTitle;
    private String eventContent;
    private String eventImg;
    private Date eventStartDate;
    private Date eventEndDate;

    public FollowContentVo(String brandName, String brandImg, String eventType, String eventTitle, String eventContent, String eventImg, Date eventStartDate, Date eventEndDate) {
        this.brandName = brandName;
        this.brandImg = brandImg;
        this.eventType = eventType;
        this.eventTitle = eventTitle;
        this.eventContent = eventContent;
        this.eventImg = eventImg;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
    }

    public FollowContentVo(String brandName, String brandImg, String eventType, String collabPlatformName, String eventTitle, String eventContent, String eventImg, Date eventStartDate, Date eventEndDate) {
        this.brandName = brandName;
        this.brandImg = brandImg;
        this.eventType = eventType;
        this.collabPlatformName = collabPlatformName;
        this.eventTitle = eventTitle;
        this.eventContent = eventContent;
        this.eventImg = eventImg;
        this.eventStartDate = eventStartDate;
        this.eventEndDate = eventEndDate;
    }
}