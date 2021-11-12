package com.kp.foodinfo.vo;

import com.kp.foodinfo.domain.CollabEvent;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class CollabEventListVo {
    List<CollabEventVo> items = null;

    public CollabEventListVo(List<CollabEvent> collabEvents) {
        items = collabEvents.stream()
                .map(collabEvent -> new CollabEventVo(collabEvent))
                .collect(Collectors.toList());
    }

    @Data
    class CollabEventVo {
        private String title;
        private String content;
        private String img;
        private Date startDate;
        private Date endDate;
        private Long brand_id;

        public CollabEventVo(CollabEvent collabEvent) {
            this.title = collabEvent.getTitle();
            this.content = collabEvent.getContent();
            this.img = collabEvent.getImg();
            this.startDate = collabEvent.getStartDate();
            this.endDate = collabEvent.getEndDate();
            this.brand_id = collabEvent.getBrand().getId();
        }
    }
}
