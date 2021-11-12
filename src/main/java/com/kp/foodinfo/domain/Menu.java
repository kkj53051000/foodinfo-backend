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
        name = "MENU_SEQ_GENERATOR",
        table = "FOODINFO_SEQUENCES",
        pkColumnValue = "MENU_SEQ", allocationSize = 50)
public class Menu {
    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "MENU_SEQ_GENERATOR")
    @Column(name = "menu_id")
    private Long id;

    @NotNull
    private String name;
    private int price;
    private String img;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brandmenukind_id")
    private BrandMenuKind brandMenuKind;

    public Menu(String name, int price, String img, BrandMenuKind brandMenuKind){
        this.name = name;
        this.price = price;
        this.img = img;
        this.brandMenuKind = brandMenuKind;
    }
}

//menu.getItem().getName();
//
//class Item {
//    public String name;
//    public String getName() {
//        return this.name;
//    }
//}
//
//class ItemProxy extends Item {
//    Item item = null;
//
//    public String getName() {
//        if(item = null) {
//            item = em.find(Item.class, 2L);
//        }
//        return item.getName();
//    }
//}
//