package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.FoodBrand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodBrandRepository extends JpaRepository<FoodBrand, Long> {
}
