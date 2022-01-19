package com.kp.foodinfo.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
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
