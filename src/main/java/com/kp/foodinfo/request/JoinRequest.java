package com.kp.foodinfo.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
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
