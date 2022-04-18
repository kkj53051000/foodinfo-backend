package com.kp.foodinfo.vo;

import com.kp.foodinfo.domain.CollabPlatform;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CollabEventInfoVo {
    private String name;
    private String img;
    private int count;

    public CollabEventInfoVo(String name, String img, int count) {
        this.name = name;
        this.img = img;
        this.count = count;
    }
}
