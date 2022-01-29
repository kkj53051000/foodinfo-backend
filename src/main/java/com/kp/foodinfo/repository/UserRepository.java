package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    int countByEmail(String email);

    Optional<User> findByEmailUuid(String uuid);
}
