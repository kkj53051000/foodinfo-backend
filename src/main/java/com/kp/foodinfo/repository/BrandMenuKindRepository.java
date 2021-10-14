package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.BrandMenuKind;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BrandMenuKindRepository extends JpaRepository<BrandMenuKind, Long> {
    public Optional<BrandMenuKind> findByPriority(int priority);

    public Optional<BrandMenuKind> findByName(String name);
}
