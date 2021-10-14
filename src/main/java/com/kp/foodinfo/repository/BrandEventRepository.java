package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.BrandEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BrandEventRepository extends JpaRepository<BrandEvent, Long> {
    public List<BrandEvent> findByBrand(Brand brand);

    public Optional<BrandEvent> findByTitle(String title);
}
