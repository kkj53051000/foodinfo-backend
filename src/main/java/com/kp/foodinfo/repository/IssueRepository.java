package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.Food;
import com.kp.foodinfo.domain.Issue;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    List<Issue> findByBrand(Brand brand);

    List<Issue> findByDate(Date date);

    @Query("SELECT i FROM Issue i where i.brand.food = ?1")
    List<Issue> findRecentlyFive(Food food, Pageable pageable);

    List<Issue> findByBrand_Food(Food food);

    @Query("SELECT i FROM Issue i where i.brand.food = ?1")
    List<Issue> findRecentlyByFood(Food food);
}
