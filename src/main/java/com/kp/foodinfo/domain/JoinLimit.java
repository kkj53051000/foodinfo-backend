package com.kp.foodinfo.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class JoinLimit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "joinlimit_id")
    private Long id;

    private int limitNum;

    public JoinLimit(int limitNum) {
        this.limitNum = limitNum;
    }
}
