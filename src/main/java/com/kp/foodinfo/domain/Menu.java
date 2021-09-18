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
        name = "MENU_SEQ_GENERATOR",
        table = "FOODINFO_SEQUENCES",
        pkColumnValue = "MENU_SEQ", allocationSize = 50)
public class Menu {
    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "MENU_SEQ_GENERATOR")
    @Column(name = "menu_id")
    private long id;

    @NotNull
    private String name;
    private int price;
    private String img;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brandmenukind_id")
    private BrandMenuKind brandMenuKind;

    public Menu(String name, int price, String img){
        this.name = name;
        this.price = price;
        this.img = img;
    }
}
