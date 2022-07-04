package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
}
