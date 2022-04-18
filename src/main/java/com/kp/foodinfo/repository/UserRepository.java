package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    int countByEmail(String email);

    Optional<User> findByEmailUuid(String uuid);

    List<User> findByJoinDateBetween(Date startDate, Date endDate);

    //    @Query("select u from User u where u.joinDate like ")
    List<User> findByJoinDateLike(Date today);

    int countByJoinDateStr(String date);
}
