package com.kp.foodinfo.vo;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class FrIssueRecentlyVo {
    private Long id;
    private Long brandId;
    private String brandName;
    private String brandImg;
    private String eventTypeName;
    private String eventTypeImg;
    private String title;
    private String content;
    private String img;
    private String startDate;
    private String endDate;
    private String type;
}
