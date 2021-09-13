package com.kp.foodinfo.form;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JoinForm {
    private String userid;
    private String userpw;
    private String nickname;
    private String email;

    public JoinForm(String userid, String userpw, String nickname, String email){
        this.userid = userid;
        this.userpw = userpw;
        this.nickname = nickname;
        this.email = email;
    }
}
