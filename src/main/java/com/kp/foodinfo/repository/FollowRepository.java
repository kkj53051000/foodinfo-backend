package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.Follow;
import com.kp.foodinfo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByBrandAndUser(Brand brand, User user);

    List<Follow> findByUser(User user);

    Optional<Follow> findByUserAndBrand(User user, Brand brand);

    long countByUser(User user);
}
