package com.kp.foodinfo.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JoinRequest {
    private String userid;
    private String userpw;
    private String email;

    public JoinRequest(String userid, String userpw, String email){
        this.userid = userid;
        this.userpw = userpw;
        this.email = email;
    }
}
