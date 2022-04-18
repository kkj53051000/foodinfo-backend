package com.kp.foodinfo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableGenerator(
        name = "MENU_SEQ_GENERATOR",
        table = "FOODINFO_SEQUENCES",
        pkColumnValue = "MENU_SEQ", allocationSize = 50)
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "MENU_SEQ_GENERATOR")
    @Column(name = "menu_id")
    private Long id;

    private String name;
    private int price;
    @Lob
    private String img;
    private int priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brandmenukind_id")
    private BrandMenuKind brandMenuKind;

    public Menu(String name, int price, String img, BrandMenuKind brandMenuKind) {
        this.name = name;
        this.price = price;
        this.img = img;
        this.brandMenuKind = brandMenuKind;
    }
}