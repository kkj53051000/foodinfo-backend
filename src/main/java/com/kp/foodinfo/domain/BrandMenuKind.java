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
        name = "BRANDMENUKIND_SEQ_GENERATOR",
        table = "FOODINFO_SEQUENCES",
        pkColumnValue = "BRANDMENUKIND_SEQ", allocationSize = 50)
public class BrandMenuKind {
    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "BRANDMENUKIND_SEQ_GENERATOR")
    @Column(name = "brandmenukind_id")
    private long id;

    @NotNull
    private String name;
    @NotNull
    private int priority;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    public BrandMenuKind(String name, int priority, Brand brand){
        this.name = name;
        this.priority = priority;
        this.brand = brand;
    }
}
