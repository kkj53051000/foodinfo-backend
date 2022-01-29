package com.kp.foodinfo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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


    private String title;
    @Lob
    private String content;
    @Lob
    private String img;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    //@JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm", timezone="Asia/Seoul")
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

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
