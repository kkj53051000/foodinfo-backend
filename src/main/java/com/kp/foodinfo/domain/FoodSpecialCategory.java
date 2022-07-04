package com.kp.foodinfo.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodSpecialCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "foodspecialcategory_id")
    private Long id;

    private String name;
    private String img;
}
