package com.kp.foodinfo.vo;

import com.kp.foodinfo.domain.SiktamEvent;
import com.kp.foodinfo.util.StringToDateUtil;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class SiktamEventListVo {

    List<SiktamEventVo> items = null;

    public SiktamEventListVo(List<SiktamEvent> siktamEvents) {
        items = siktamEvents.stream()
                .map(siktamEvent -> new SiktamEventVo(siktamEvent))
                .collect(Collectors.toList());
    }

    @Data
    class SiktamEventVo {
        private Long id;
        private String title;
        private String img;
        private String startDate;
        private String endDate;
        private int winnerCount;
        private boolean status;

        public SiktamEventVo(SiktamEvent siktamEvent) {
            this.id = siktamEvent.getId();
            this.title = siktamEvent.getTitle();
            this.img = siktamEvent.getImg();
            this.startDate = StringToDateUtil.dateToStringProcess(siktamEvent.getStartDate());
            this.endDate = StringToDateUtil.dateToStringProcess(siktamEvent.getEndDate());
            this.winnerCount = siktamEvent.getWinnerCount();

            Date now = new Date();
            if(now.after(siktamEvent.getEndDate())){
                this.status = false;
            }else{
                this.status = true;
            }
        }
    }
}
