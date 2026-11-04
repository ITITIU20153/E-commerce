package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.CartEntity;
import com.example.demo.entity.CartItemEntity;

@Repository 
public interface CartItemRepository extends JpaRepository<CartItemEntity,Long> {
    
}
