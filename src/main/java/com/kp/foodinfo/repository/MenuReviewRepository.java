package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.MenuReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuReviewRepository extends JpaRepository<MenuReview, Long> {
}
