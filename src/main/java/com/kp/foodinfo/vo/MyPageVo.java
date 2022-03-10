package com.kp.foodinfo.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyPageVo {
    private long id;
    private String email;
    private int followCount;

    public MyPageVo(long id, String email, int followCount){
        this.id = id;
        this.email = email;
        this.followCount = followCount;
    }
}
