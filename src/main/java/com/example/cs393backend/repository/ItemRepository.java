package com.example.AmazonBackendClone.repository;

import com.example.AmazonBackendClone.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
}

