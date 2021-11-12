package com.kp.foodinfo.vo;

import com.kp.foodinfo.domain.EventType;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class EventTypeListVo {
    private List<EventTypeVo> items = null;

    public EventTypeListVo(List<EventType> eventTypes) {
        this.items = eventTypes.stream()
                .map(eventType -> new EventTypeVo(eventType))
                .collect(Collectors.toList());

    }

    @Data
    public class EventTypeVo {
        private Long id;
        private String name;

        public EventTypeVo(EventType eventType) {
            this.id = eventType.getId();
            this.name = eventType.getName();
        }
    }
}
