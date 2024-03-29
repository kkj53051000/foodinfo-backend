package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.SiktamEvent;
import com.kp.foodinfo.domain.SiktamEventEntry;
import com.kp.foodinfo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SiktamEventEntryRepository extends JpaRepository<SiktamEventEntry, Long> {
    List<SiktamEventEntry> findBySiktamEvent(SiktamEvent siktamEvent);

    int countByUserAndSiktamEvent(User user, SiktamEvent siktamEvent);
}
