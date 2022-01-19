package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.SiktamEvent;
import com.kp.foodinfo.domain.SiktamEventWinner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SiktamEventWinnerRepository extends JpaRepository<SiktamEventWinner, Long> {
    List<SiktamEventWinner> findBySiktamEvent(SiktamEvent siktamEvent);
}