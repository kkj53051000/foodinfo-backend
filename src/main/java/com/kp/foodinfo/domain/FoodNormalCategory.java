package com.kp.foodinfo.domain;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodNormalCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "foodnormalcategory_id")
    private Long id;

    private String name;
    private String img;
}
