package com.example.cs393backend.repository;
import com.example.cs393backend.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

    ProductEntity findByName(String name);
}
