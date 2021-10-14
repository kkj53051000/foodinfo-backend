package com.kp.foodinfo.domain;

import com.sun.istack.NotNull;
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
        table = "FOODINFO_SEQUENCES",
        pkColumnValue = "USER_SEQ", allocationSize = 50)
public class User{
    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "USER_SEQ_GENERATOR")
    @Column(name = "user_id")
    private long id;

    @NotNull
    private String userid;
    @NotNull
    private String userpw;
    @NotNull
    private String email;
    @NotNull
    private Date joinDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String userid, String userpw, String email, Date joinDate, Role role){
        this.userid = userid;
        this.userpw = userpw;
        this.email = email;
        this.joinDate = joinDate;
        this.role = role;
    }
}
