package com.kp.foodinfo.domain;

import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@TableGenerator(
        name = "BRAND_SEQ_GENERATOR",
        table = "FOODINFO_SEQUENCES",
        pkColumnValue = "BRAND_SEQ", allocationSize = 50)
public class Brand {
    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "BRAND_SEQ_GENERATOR")
    @Column(name = "brand_id")
    private Long id;

    @NotNull
    private String name;
    @NotNull
    private String img;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    private Food food;

    public Brand(String name, String img, Food food){
        this.name = name;
        this.img = img;
        this.food = food;
    }
}
