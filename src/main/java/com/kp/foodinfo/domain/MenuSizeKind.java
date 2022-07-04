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
        name = "MENUSIZEKIND_SEQ_GENERATOR",
        table = "FOODINFO_SEQUENCES",
        pkColumnValue = "MENUSIZEKIND_SEQ", allocationSize = 50)
public class MenuSizeKind {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "MENUSIZEKIND_SEQ_GENERATOR")
    @Column(name = "menusizekind_id")
    private Long id;

    private String size;

    public MenuSizeKind(String size) {
        this.size = size;
    }
}
