package com.kp.foodinfo.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CollabEventMenuListVo {
    private String name;
    private String img;

    public CollabEventMenuListVo(String name, String img) {
        this.name = name;
        this.img = img;
    }
}
