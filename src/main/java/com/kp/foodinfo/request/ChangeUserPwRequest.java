package com.kp.foodinfo.request;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ChangeUserPwRequest {
    private String nowUserPw;
    private String changeUserPw;
}
