package com.kp.foodinfo.request;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
public class JoinRequest {
    private String email;
    private String userpw;

    public JoinRequest(String email, String userpw) {
        this.email = email;
        this.userpw = userpw;
    }
}
