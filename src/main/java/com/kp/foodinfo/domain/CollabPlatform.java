package com.kp.foodinfo.domain;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@TableGenerator(
        name = "COLLABPLATFORM_SEQ_GENERATOR",
        table = "FOODINFO_SEQUENCES",
        pkColumnValue = "COLLABPLATFORM_SEQ", allocationSize = 50)
public class CollabPlatform {
    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "COLLABPLATFORM_SEQ_GENERATOR")
    @Column(name = "collabplatform_id")
    private long id;

    @NotNull
    private String name;
    private String img;

    public CollabPlatform(String name, String img) {
        this.name = name;
        this.img = img;
    }
}
