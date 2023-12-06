package com.example.AmazonBackendClone.repository;

import com.example.AmazonBackendClone.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
}
