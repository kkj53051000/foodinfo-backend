package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Optional<Brand> findByName(String name);

    List<Brand> findByFood(Food food);

    List<Brand> findByRecentlyUpdate(Date date);
}
