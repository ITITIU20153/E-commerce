package com.example.demo.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity 
@Table(name = "cart_table")
public class CartEntity extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @Column(name = "totalQuantity",nullable = false)
    private Integer totalQuantity;
    @Column(name = "totalAmount",nullable = false)
    private BigDecimal totalAmount;
    @OneToMany(mappedBy = "cartEntity",
        fetch = FetchType.LAZY,
        cascade = CascadeType.ALL
    )
    private List<CartItemEntity> cartItemEntities;

    public List<CartItemEntity> getCartItemEntities() {
        return cartItemEntities;
    }
    public void setCartItemEntities(List<CartItemEntity> cartItemEntities) {
        this.cartItemEntities = cartItemEntities;
    }
    public UserEntity getUser() {
        return user;
    }
    public void setUser(UserEntity user) {
        this.user = user;
    }
    public Integer getTotalQuantity() {
        return totalQuantity;
    }
    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
