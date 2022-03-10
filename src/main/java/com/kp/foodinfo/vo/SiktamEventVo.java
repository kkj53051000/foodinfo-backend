package com.kp.foodinfo.vo;

import com.kp.foodinfo.domain.SiktamEvent;
import com.kp.foodinfo.util.DateFormatUtil;
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
        this.startDate = DateFormatUtil.dateToStringProcess(siktamEvent.getStartDate());
        this.endDate = DateFormatUtil.dateToStringProcess(siktamEvent.getEndDate());
        this.winnerCount = siktamEvent.getWinnerCount();
    }
}
