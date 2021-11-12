package com.kp.foodinfo.domain;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@TableGenerator(
        name = "NOTICE_SEQ_GENERATOR",
        table = "FOODINFO_SEQUENCES",
        pkColumnValue = "NOTICE_SEQ", allocationSize = 50)
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "NOTICE_SEQ_GENERATOR")
    @Column(name = "notice_id")
    private Long id;

    @NotNull
    private String title;
    @NotNull
    private String content;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Notice(String title, String content, User user) {
        this.title = title;
        this.content = content;
        this.user = user;
    }
}
