package com.kp.foodinfo.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@TableGenerator(
        name = "BRAND_SEQ_GENERATOR",
        table = "FOODINFO_SEQUENCES",
        pkColumnValue = "BRAND_SEQ", allocationSize = 50)
public class Brand {
    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "BRAND_SEQ_GENERATOR")
    @Column(name = "brand_id")
    private long id;

    @NotNull
    private String name;
    @NotNull
    private String img;

    public Brand(String name, String img){
        this.name = name;
        this.img = img;
    }
}
