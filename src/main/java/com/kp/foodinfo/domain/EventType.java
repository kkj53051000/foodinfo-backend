package com.kp.foodinfo.domain;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
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

    @NotNull
    private String name;

    @NotNull
    private String img;

    public EventType(String name, String img) {
        this.name = name;
        this.img = img;
    }
}
