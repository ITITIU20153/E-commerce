package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.CartEntity;
import com.example.demo.entity.CartItemEntity;

@Repository 
public interface CartItemRepository extends JpaRepository<CartItemEntity,Long> {
    Optional<CartItemEntity> findByCartEntity_IdAndProductEntity_Id(
        Long cartId,
        Long productId
    );
}
