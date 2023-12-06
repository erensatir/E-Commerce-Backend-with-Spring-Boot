package com.example.AmazonBackendClone.repository;

import com.example.AmazonBackendClone.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    ProductEntity findByName(String name);
}
