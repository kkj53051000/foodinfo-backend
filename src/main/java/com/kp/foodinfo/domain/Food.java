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
@TableGenerator(
        name = "FOOD_SEQ_GENERATOR",
        table = "FOODINFO_SEQUENCES",
        pkColumnValue = "FOOD_SEQ", allocationSize = 50)
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "FOOD_SEQ_GENERATOR")
    @Column(name = "food_id")
    private Long id;

    private String name;
    @Lob
    private String img;
    private int priority;

    public Food(String name, String img) {
        this.name = name;
        this.img = img;
    }
}
