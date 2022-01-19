package com.kp.foodinfo.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SiktamEventWinnerVo {
    private String email;
    private String phoneNumber;

    public SiktamEventWinnerVo(String email, String phoneNumber) {
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
