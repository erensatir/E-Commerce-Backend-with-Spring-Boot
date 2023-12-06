package com.example.AmazonBackendClone.repository;

import com.example.AmazonBackendClone.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity, Long> {
}

