package com.kp.foodinfo.request;

import lombok.Getter;

@Getter
public class SiktamEventRequest {
    private String title;
    private String startDate;
    private String endDate;
    private int winnerCount;
}
