package com.kp.foodinfo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@Builder
@TableGenerator(
        name = "EVENT_SEQ_GENERATOR",
        table = "FOODINFO_SEQUENCES",
        pkColumnValue = "EVENT_SEQ", allocationSize = 50)
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "EVENT_SEQ_GENERATOR")
    @Column(name = "event_id")
    private Long id;

    @NotNull
    private String title;
    private String content;
    private String img;

    @NotNull
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm", timezone="Asia/Seoul")
    private Date startDate;

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm", timezone="Asia/Seoul")
    private Date endDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventtype_id")
    private EventType eventType;

    public Event(String title, String content, String img, Date startDate, Date endDate, Brand brand, EventType eventType) {
        this.title = title;
        this.content = content;
        this.img = img;
        this.startDate = startDate;
        this.endDate = endDate;
        this.brand = brand;
        this.eventType = eventType;
    }

}
