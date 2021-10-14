package com.kp.foodinfo.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginRequest {
    private String userid;
    private String userpw;

    public LoginRequest(String userid, String userpw){
        this.userid = userid;
        this.userpw = userpw;
    }
}
