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
        name = "BRANDEVENT_SEQ_GENERATOR",
        table = "FOODINFO_SEQUENCES",
        pkColumnValue = "BRANDEVENT_SEQ", allocationSize = 50)
public class BrandEvent implements Event {
    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "BRANDEVENT_SEQ_GENERATOR")
    @Column(name = "brandevent_id")
    private long id;

    @NotNull
    private String title;
    @NotNull
    private String content;
    private String img;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    public BrandEvent(String title, String content, String img, Brand brand){
        this.title = title;
        this.content = content;
        this.img = img;
        this.brand = brand;
    }
}
