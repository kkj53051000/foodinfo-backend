package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.BrandMenuKind;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BrandMenuKindRepository extends JpaRepository<BrandMenuKind, Long> {
    Optional<BrandMenuKind> findByPriority(int priority);

    Optional<BrandMenuKind> findByName(String name);

    List<BrandMenuKind> findByBrand(Brand brand);
}
