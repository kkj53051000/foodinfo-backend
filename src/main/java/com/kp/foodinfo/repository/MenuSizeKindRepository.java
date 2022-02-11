package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.MenuSizeKind;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuSizeKindRepository extends JpaRepository<MenuSizeKind, Long> {
    int countBySize(String size);

    Optional<MenuSizeKind> findBySize(String size);
}
