package com.kp.foodinfo.vo;

import com.kp.foodinfo.domain.Event;
import com.kp.foodinfo.util.StringToDateUtil;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class EventListVo {

    List<EventVo> items = null;

    public EventListVo(List<Event> events) {
        this.items = events.stream()
                .map(event -> new EventVo(event))
                .collect(Collectors.toList());
    }

    @Data
    class EventVo {
        private long id;
        private String title;
        private String content;
        private String img;
        private String startDate;
        private String endDate;
        private String eventTypeName;
        private String eventTypeImg;

        public EventVo(Event event) {
            this.id = event.getId();
            this.title = event.getTitle();
            this.content = event.getContent();
            this.img = event.getImg();
            this.startDate = StringToDateUtil.dateToStringProcess(event.getStartDate());
            this.endDate = StringToDateUtil.dateToStringProcess(event.getEndDate());
            this.eventTypeName = event.getEventType().getName();
            this.eventTypeImg = event.getEventType().getImg();
        }
    }
}