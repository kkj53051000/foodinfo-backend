package com.kp.foodinfo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@TableGenerator(
        name = "MENUSIZE_SEQ_GENERATOR",
        table = "FOODINFO_SEQUENCES",
        pkColumnValue = "MENUSIZE_SEQ", allocationSize = 50)
public class MenuSize {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "MENUSIZE_SEQ_GENERATOR")
    @Column(name = "menusize_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menusizekind_id")
    private MenuSizeKind menuSizeKind;

    private int price;

    public MenuSize(Menu menu, MenuSizeKind menuSizeKind, int price) {
        this.menu = menu;
        this.menuSizeKind = menuSizeKind;
        this.price = price;
    }
}
