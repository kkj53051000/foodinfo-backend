package com.kp.foodinfo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@TableGenerator(
        name = "SIKTAMEVENTENTRY_SEQ_GENERATOR",
        table = "FOODINFO_SEQUENCES",
        pkColumnValue = "SIKTAMEVENTENTRY_SEQ", allocationSize = 50)
public class SiktamEventEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SIKTAMEVENTENTRY_SEQ_GENERATOR")
    @Column(name = "siktamevententry_id")
    private Long id;

    private String phoneNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "siktamevent_id")
    private SiktamEvent  siktamEvent;

    public SiktamEventEntry(String phoneNumber, User user, SiktamEvent siktamEvent) {
        this.phoneNumber = phoneNumber;
        this.user = user;
        this.siktamEvent  = siktamEvent;
    }
}
