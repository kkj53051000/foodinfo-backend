package com.kp.foodinfo.vo;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserVo {
    private String status;
    private String jwtKey;
    private String email;
    private boolean adminCheck;

    public UserVo(String status, String jwtKey, String email, boolean adminCheck) {
        this.status = status;
        this.jwtKey = jwtKey;
        this.email = email;
        this.adminCheck = adminCheck;
    }
}
