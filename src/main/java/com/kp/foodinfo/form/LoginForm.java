package com.kp.foodinfo.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class LoginForm {
    private String userid;
    private String userpw;

    public LoginForm(String userid, String userpw){
        this.userid = userid;
        this.userpw = userpw;
    }
}
