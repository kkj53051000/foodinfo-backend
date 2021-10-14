package com.kp.foodinfo.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class BasicVo {
    private String status;
    private String cause;

    public BasicVo(String status){
        this.status = status;
    }
    public BasicVo(String status, String cause){
        this.status = status;
        this.cause = cause;
    }
}
