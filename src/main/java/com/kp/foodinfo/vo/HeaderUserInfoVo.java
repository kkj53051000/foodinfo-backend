package com.kp.foodinfo.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor
public class HeaderUserInfoVo {
    private String email;

    public HeaderUserInfoVo(String email) {
        this.email = email;
    }
}
