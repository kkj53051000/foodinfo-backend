package com.kp.foodinfo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
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
