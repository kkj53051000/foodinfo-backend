package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
    List<FoodItem> findAllByDateAfter(LocalDate localDate);

    List<FoodItem> findTop10ByOrderByIdDesc();

    List<FoodItem> findByFoodNormalCategory_Id(long id);

    List<FoodItem> findByFoodSpecialCategory_Id(long id);
}
