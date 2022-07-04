package com.kp.foodinfo.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "fooditem_id")
    private Long id;

    private String name;
    private String img;
    private int price;
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "foodbrand_id")
    private FoodBrand foodBrand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "foodnormalcategory_id")
    private FoodNormalCategory foodNormalCategory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "foodspecialcategory_id")
    private FoodSpecialCategory foodSpecialCategory;
}
