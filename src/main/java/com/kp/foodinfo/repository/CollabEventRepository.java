package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.CollabEvent;
import com.kp.foodinfo.domain.CollabPlatform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CollabEventRepository extends JpaRepository<CollabEvent, Long> {
    int countByBrandAndCollabPlatform(Brand brand, CollabPlatform collabPlatform);

    Optional<CollabEvent> findByTitle(String title);

    List<CollabEvent> findByBrand(Brand brand);

    List<CollabEvent> findByBrandAndCollabPlatform(Brand brand, CollabPlatform collabPlatform);
}
