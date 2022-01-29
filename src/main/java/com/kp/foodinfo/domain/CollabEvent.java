package com.kp.foodinfo.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@TableGenerator(
        name = "COLLABEVENT_SEQ_GENERATOR",
        table = "FOODINFO_SEQUENCES",
        pkColumnValue = "COLLABEVENT_SEQ", allocationSize = 50)
public class CollabEvent {
    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "COLLABEVENT_SEQ_GENERATOR")
    @Column(name = "collabevent_id")
    private Long id;

    private String title;
    private String content;
    private String img;

    private Date startDate;
    private Date endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "collabplatform_id")
    private CollabPlatform collabPlatform;

    public CollabEvent(String title, String content, String img, Date startDate, Date endDate, Brand brand, CollabPlatform collabPlatform){
        this.title = title;
        this.content = content;
        this.img = img;
        this.startDate = startDate;
        this.endDate = endDate;
        this.brand = brand;
        this.collabPlatform = collabPlatform;
    }
}
