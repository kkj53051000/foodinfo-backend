package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.Brand;
import com.kp.foodinfo.domain.Event;
import com.kp.foodinfo.domain.Food;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByTitle(String title);

    List<Event> findByBrand(Brand brand);

    List<Event> findByStartDate(Date date);

    @Query("SELECT e FROM Event e where e.brand.food = ?1")
    List<Event> findRecentlyFive(Food food, Pageable pageable);

    // @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT e FROM Event e where e.brand.food = ?1")
    List<Event> findRecentlyByFood(Food food);
}
