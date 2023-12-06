package com.example.AmazonBackendClone.repository;

import com.example.AmazonBackendClone.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
}