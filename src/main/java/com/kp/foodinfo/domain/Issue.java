package com.kp.foodinfo.domain;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableGenerator(
        name = "ISSUE_SEQ_GENERATOR",
        table = "FOODINFO_SEQUENCES",
        pkColumnValue = "ISSUE_SEQ", allocationSize = 50)
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "ISSUE_SEQ_GENERATOR")
    @Column(name = "issue_id")
    private Long id;

    private String title;
    @Lob
    private String content;
    @Lob
    private String img;

    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    public Issue(String title, String content, String img, Date date, Brand brand) {
        this.title = title;
        this.content = content;
        this.img = img;
        this.date = date;
        this.brand = brand;
    }
}
