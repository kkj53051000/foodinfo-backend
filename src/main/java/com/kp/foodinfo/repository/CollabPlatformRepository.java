package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.CollabPlatform;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CollabPlatformRepository extends JpaRepository<CollabPlatform, Long> {
    public Optional<CollabPlatform> findByName(String name);
}
