package com.kp.foodinfo.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodBrand {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "foodbrand_id")
    private Long id;

    private String name;
    private String img;
}
