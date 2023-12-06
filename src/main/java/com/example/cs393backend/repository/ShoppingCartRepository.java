package com.example.cs393backend.repository;

import com.example.cs393backend.entity.ShoppingCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCartEntity, Long> {
}
