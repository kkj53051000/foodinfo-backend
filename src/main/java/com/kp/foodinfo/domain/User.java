package com.kp.foodinfo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter @Setter
@NoArgsConstructor
@TableGenerator(
        name = "USER_SEQ_GENERATOR",
        table = "RECOG_SEQUENCES",
        pkColumnValue = "USER_SEQ", allocationSize = 50)
public class User {
    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "USER_SEQ_GENERATOR")
    @Column(name = "user_id")
    private long id;

    private String userid;
    private String userpw;
    private String nickname;
    private String email;
    private Date joinDate;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String userid, String userpw, String nickname, String email, Date joinDate, Role role){
        this.userid = userid;
        this.userpw = userpw;
        this.nickname = nickname;
        this.email = email;
        this.joinDate = joinDate;
        this.role = role;
    }
}
