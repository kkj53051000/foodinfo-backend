package com.kp.foodinfo.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
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
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Temporal(TemporalType.DATE)
    private Date endDate;

    private int startDateInt;
    private int endDateInt;

    private int winnerCount;
    private boolean status;

    @Builder
    public SiktamEvent(String title, String img, Date startDate, Date endDate, int startDateInt, int endDateInt, int winnerCount, boolean status) {
        this.title = title;
        this.img = img;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startDateInt = startDateInt;
        this.endDateInt = endDateInt;
        this.winnerCount = winnerCount;
        this.status = status;
    }
}
