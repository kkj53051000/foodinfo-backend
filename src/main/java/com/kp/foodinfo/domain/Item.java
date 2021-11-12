package com.kp.foodinfo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Data
public class Item implements Serializable {
    @Id @GeneratedValue
    private Long id;

    private String title;
}

