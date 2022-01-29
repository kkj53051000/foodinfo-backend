package com.kp.foodinfo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@TableGenerator(
        name = "NOTICE_SEQ_GENERATOR",
        table = "FOODINFO_SEQUENCES",
        pkColumnValue = "NOTICE_SEQ", allocationSize = 50)
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "NOTICE_SEQ_GENERATOR")
    @Column(name = "notice_id")
    private Long id;

    private String title;
    @Lob
    private String content;

    private Date date;


    public Notice(String title, String content, Date date) {
        this.title = title;
        this.content = content;
        this.date = date;
    }
}
