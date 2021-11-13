package com.kp.foodinfo.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserVo {
    private String status;
    private String jwtKey;
    private boolean adminCheck;

    public UserVo(String status, String jwtKey, boolean adminCheck) {
        this.status = status;
        this.jwtKey = jwtKey;
        this.adminCheck = adminCheck;
    }
}
