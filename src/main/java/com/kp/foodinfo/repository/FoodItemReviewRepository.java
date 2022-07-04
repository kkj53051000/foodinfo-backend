package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.FoodItemReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodItemReviewRepository extends JpaRepository<FoodItemReview, Long> {
}
