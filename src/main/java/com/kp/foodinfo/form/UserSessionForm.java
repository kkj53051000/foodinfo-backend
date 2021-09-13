package com.kp.foodinfo.form;

import com.kp.foodinfo.domain.Role;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor
public class UserSessionForm {
    private String userid;
    private String nickname;
    private String email;
    private Date joinDate;
    private Role role;

    public UserSessionForm(String userid, String nickname, String email, Date joinDate, Role role){
        this.userid = userid;
        this.nickname = nickname;
        this.email = email;
        this.joinDate = joinDate;
        this.role = role;
    }
}
