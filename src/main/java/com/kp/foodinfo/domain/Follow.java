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
        name = "FOLLOW_SEQ_GENERATOR",
        table = "FOODINFO_SEQUENCES",
        pkColumnValue = "FOLLOW_SEQ", allocationSize = 50)
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "FOLLOW_SEQ_GENERATOR")
    @Column(name = "follow_id")
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    public Follow(User user, Brand brand) {
        this.user = user;
        this.brand = brand;
    }
}
