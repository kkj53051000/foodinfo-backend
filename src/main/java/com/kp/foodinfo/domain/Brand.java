package com.kp.foodinfo.domain;

import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@TableGenerator(
        name = "BRAND_SEQ_GENERATOR",
        table = "FOODINFO_SEQUENCES",
        pkColumnValue = "BRAND_SEQ", allocationSize = 50)
public class Brand implements Comparable<Brand> {
    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "BRAND_SEQ_GENERATOR")
    @Column(name = "brand_id")
    private Long id;

    private String name;
    @Lob
    private String img;
    private Date recentlyUpdate;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    private Food food;

    public Brand(String name, String img, Date recentlyUpdate, Food food){
        this.name = name;
        this.img = img;
        this.recentlyUpdate = recentlyUpdate;
        this.food = food;
    }

    @Override
    public int compareTo(Brand o) {
        return o.getRecentlyUpdate().compareTo(getRecentlyUpdate());
    }
}
