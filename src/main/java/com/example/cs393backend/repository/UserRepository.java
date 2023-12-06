package com.example.cs393backend.repository;

import com.example.cs393backend.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

// UserRepository.java
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUsername(String username);

    UserEntity findByEmail(String email);
}