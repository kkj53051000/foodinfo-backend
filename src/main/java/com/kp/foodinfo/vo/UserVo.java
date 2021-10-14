package com.kp.foodinfo.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserVo {
    private String status;
    private String jwtKey;

    public UserVo(String status, String jwtKey) {
        this.status = status;
        this.jwtKey = jwtKey;
    }
}
