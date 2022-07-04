package com.kp.foodinfo.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodItemReview {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "fooditemreview_id")
    private Long id;

    private String reviewContent;
    private byte rePurchase;
    private boolean deleteAt;
    private String ipAddress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fooditem_id")
    private FoodItem foodItem;
}
