package com.kp.foodinfo.vo;

import com.kp.foodinfo.domain.SiktamEvent;
import com.kp.foodinfo.util.StringToDateUtil;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SiktamEventVo {
    private String title;
    private String img;
    private String startDate;
    private String endDate;
    private int winnerCount;

    public SiktamEventVo(SiktamEvent siktamEvent) {
        this.title = siktamEvent.getTitle();
        this.img = siktamEvent.getImg();
        this.startDate = StringToDateUtil.dateToStringProcess(siktamEvent.getStartDate());
        this.endDate = StringToDateUtil.dateToStringProcess(siktamEvent.getEndDate());
        this.winnerCount = siktamEvent.getWinnerCount();
    }
}
