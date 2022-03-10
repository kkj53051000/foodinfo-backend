package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.SiktamEvent;
import com.kp.foodinfo.domain.User;
import com.kp.foodinfo.service.SiktamEventService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SiktamEventRepository extends JpaRepository<SiktamEvent, Long> {
}
