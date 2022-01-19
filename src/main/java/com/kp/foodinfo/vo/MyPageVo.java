package com.kp.foodinfo.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MyPageVo {
    private String email;
    private int followCount;

    public MyPageVo(String email, int followCount){
        this.email = email;
        this.followCount = followCount;
    }
}
