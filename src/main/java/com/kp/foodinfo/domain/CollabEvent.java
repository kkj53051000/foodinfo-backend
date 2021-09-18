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
        name = "COLLABEVENT_SEQ_GENERATOR",
        table = "FOODINFO_SEQUENCES",
        pkColumnValue = "COLLABEVENT_SEQ", allocationSize = 50)
public class CollabEvent implements Event {
    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "COLLABEVENT_SEQ_GENERATOR")
    @Column(name = "collabevent_id")
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

    public CollabEvent(String title, String content, String img, Brand brand){
        this.title = title;
        this.content = content;
        this.img = img;
        this.brand = brand;
    }
}
