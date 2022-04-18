package com.kp.foodinfo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
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
        name = "BRANDEVENT_SEQ_GENERATOR",
        table = "FOODINFO_SEQUENCES",
        pkColumnValue = "BRANDEVENT_SEQ", allocationSize = 50)
public class BrandEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "BRANDEVENT_SEQ_GENERATOR")
    @Column(name = "brandevent_id")
    private Long id;

    private String title;
    private String content;
    private String img;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Date startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
    private Date endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    public BrandEvent(String title, String content, String img, Date startDate, Date endDate, Brand brand) {
        this.title = title;
        this.content = content;
        this.img = img;
        this.startDate = startDate;
        this.endDate = endDate;
        this.brand = brand;
    }
}
