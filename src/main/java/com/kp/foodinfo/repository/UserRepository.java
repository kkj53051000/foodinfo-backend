package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByUserid(String userid);
}
