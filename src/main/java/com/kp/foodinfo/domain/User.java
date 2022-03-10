package com.kp.foodinfo.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@TableGenerator(
        name = "USER_SEQ_GENERATOR",
        table = "FOODINFO_SEQUENCES",
        pkColumnValue = "USER_SEQ", allocationSize = 50)
public class User{
    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "USER_SEQ_GENERATOR")
    @Column(name = "user_id")
    private Long id;

    private String email;
    private String userpw;
    private Date joinDate;
    private String joinDateStr;
    private Date recentlyVisitDate;
    private String emailUuid;
    private boolean emailCheck;
    private boolean deleteAt;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String email, String userpw, Date joinDate, String joinDateStr, Date recentlyVisitDate, String emailUuid, boolean emailCheck, Role role, boolean deleteAt){
        this.email = email;
        this.userpw = userpw;
        this.joinDate = joinDate;
        this.joinDateStr = joinDateStr;
        this.recentlyVisitDate = recentlyVisitDate;
        this.emailUuid = emailUuid;
        this.emailCheck = emailCheck;
        this.role = role;
        this.deleteAt = deleteAt;
    }
}
