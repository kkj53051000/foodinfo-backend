package com.kp.foodinfo.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@TableGenerator(
        name = "EVENTTYPE_SEQ_GENERATOR",
        table = "FOODINFO_SEQUENCES",
        pkColumnValue = "EVENTTYPE_SEQ", allocationSize = 50)
public class EventType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "EVENTTYPE_SEQ_GENERATOR")
    @Column(name = "eventtype_id")
    private Long id;

    private String name;

    @Lob
    private String img;

    public EventType(String name, String img) {
        this.name = name;
        this.img = img;
    }
}
