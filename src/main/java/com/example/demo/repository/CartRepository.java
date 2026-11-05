package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.CartEntity;

@Repository 
public interface CartRepository extends JpaRepository<CartEntity,Long> {
    Optional<CartEntity> findByUser_Id(Long userId);
}
