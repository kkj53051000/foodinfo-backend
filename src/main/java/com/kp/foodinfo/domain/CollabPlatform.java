package com.kp.foodinfo.domain;

import com.sun.istack.internal.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@TableGenerator(
        name = "COLLABPLATFORM_SEQ_GENERATOR",
        table = "FOODINFO_SEQUENCES",
        pkColumnValue = "COLLABPLATFORM_SEQ", allocationSize = 50)
public class CollabPlatform {
    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "COLLABPLATFORM_SEQ_GENERATOR")
    @Column(name = "collabplatform_id")
    private Long id;

    private String name;
    @Lob
    private String img;

    public CollabPlatform(String name, String img) {
        this.name = name;
        this.img = img;
    }
}
