package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_items_table")
public class OrderItemsEntity extends BaseEntity {
    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;
    
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    public OrderEntity getOrderEntity() {
        return orderEntity;
    }
    public void setOrderEntity(OrderEntity orderEntity) {
        this.orderEntity = orderEntity;
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
