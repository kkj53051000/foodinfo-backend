package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    String randomQuery = "SELECT b FROM Brand b WHERE b.food.id = :food_id order by random()";


    Optional<Brand> findByName(String name);

    List<Brand> findByFood(Food food);

    List<Brand> findByRecentlyUpdate(Date date);

    @Query(randomQuery)
    List<Brand> findRandomBrandList(long food_id);

    List<Brand> findByFoodOrderByNameAsc(Food food);
}
