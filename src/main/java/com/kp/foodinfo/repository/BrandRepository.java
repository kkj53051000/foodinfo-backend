package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    public Optional<Brand> findByName(String name);
}
