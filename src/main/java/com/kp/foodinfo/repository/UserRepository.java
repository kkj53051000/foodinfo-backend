package com.kp.foodinfo.repository;

import com.kp.foodinfo.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUserid(String userid);
}
