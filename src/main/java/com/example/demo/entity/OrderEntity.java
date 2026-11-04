package com.example.demo.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.example.demo.enums.OrderStatusEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity 
@Table(name = "order_table")
public class OrderEntity extends BaseEntity {
    
    // @Column(nullable = true)
    // private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id") 
    private UserEntity userEntity;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatusEnum status;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "total_quantity")
    private Integer totalQuantity;

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }


    @Column(name = "note",nullable = true)
    private String note;
    
    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    @CreationTimestamp 
    @Column(updatable = false)
    private LocalDateTime createAt;

    @OneToMany(mappedBy = "orderEntity"
    ,fetch = FetchType.LAZY
    ,orphanRemoval = true
    ,cascade = CascadeType.ALL)
    private List<OrderItemsEntity> orderItemsEntity;
    
    public List<OrderItemsEntity> getOrderItemsEntity() {
        return orderItemsEntity;
    }

    public void setOrderItemsEntity(List<OrderItemsEntity> orderItemsEntity) {
        this.orderItemsEntity = orderItemsEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public OrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }
}
