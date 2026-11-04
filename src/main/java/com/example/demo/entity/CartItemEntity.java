package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity 
@Table(name = "cart_items_table")
public class CartItemEntity extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private CartEntity cartEntity;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;
    
    @Column(name = "quantity")
    private Integer quantity;
    
    public CartEntity getCartEntity() {
        return cartEntity;
    }
    public void setCartEntity(CartEntity cartEntity) {
        this.cartEntity = cartEntity;
    }
    public ProductEntity getProductEntity() {
        return productEntity;
    }
    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
