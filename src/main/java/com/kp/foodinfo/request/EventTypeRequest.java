package com.kp.foodinfo.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class EventTypeRequest {
    private String name;

    public EventTypeRequest(String name) {
        this.name = name;
    }
}
