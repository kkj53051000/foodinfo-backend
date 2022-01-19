package com.kp.foodinfo.request;

import lombok.Getter;
import lombok.Setter;

@Getter
public class LoginRequest {
    private String email;
    private String userpw;

    public LoginRequest(String email, String userpw){
        this.email = email;
        this.userpw = userpw;
    }
}
