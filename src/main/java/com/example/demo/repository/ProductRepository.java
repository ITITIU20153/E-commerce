package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ProductEntity;

@Repository 
public interface ProductRepository extends JpaRepository<ProductEntity,Long> {
    Optional<ProductEntity> findById(Long id);
}
