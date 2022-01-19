package com.kp.foodinfo.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@TableGenerator(
        name = "SIKTAMEVENT_SEQ_GENERATOR",
        table = "FOODINFO_SEQUENCES",
        pkColumnValue = "SIKTAMEVENT_SEQ", allocationSize = 50)
public class SiktamEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SIKTAMEVENT_SEQ_GENERATOR")
    @Column(name = "siktamevent_id")
    private Long id;

    private String title;
    @Lob
    private String img;
    private Date startDate;
    private Date endDate;
    private int winnerCount;
    private boolean status;

    @Builder
    public SiktamEvent(String title, String img, Date startDate, Date endDate, int winnerCount, boolean status) {
        this.title = title;
        this.img = img;
        this.startDate =startDate;
        this.endDate = endDate;
        this.winnerCount = winnerCount;
        this.status = status;
    }
}
