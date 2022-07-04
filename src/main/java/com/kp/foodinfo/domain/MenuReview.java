package com.kp.foodinfo.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuReview {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "menureview_id")
    private Long id;

    private String reviewContent;
    private byte rePurchase;
    private boolean deleteAt;
    private String ipAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;
}
