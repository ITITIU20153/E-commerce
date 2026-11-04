package com.example.demo.entity;


import java.math.BigDecimal;
import java.util.List;

import com.example.demo.enums.ProductStatusEnum;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity 
@Table(name = "product_table")
public class ProductEntity extends BaseEntity {
    
    @Column(name = "product_name",unique =  true, nullable = false)
    private String productName;

    @Column(name = "product_description")
    private String productDescription;

    @Column(name = "product_pricae",nullable = false)
    private BigDecimal price;

    @Column(name = "product_quantity",nullable = false)
    private Integer stockQuantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private ProductStatusEnum status;
   
    @OneToMany(mappedBy = "productEntity",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<OrderItemsEntity> orderItems;

    @OneToMany(mappedBy = "productEntity",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    private List<CartItemEntity> cartItemEntities;

    public ProductEntity() {}
    public ProductEntity(String productName, 
        String productDescription, 
        BigDecimal price, 
        Integer stockQuantity, 
        ProductStatusEnum status) {

        this.productName = productName;
        this.productDescription = productDescription;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.status = status;
    }

    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public String getProductDescription() {
        return productDescription;
    }
    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public Integer getStockQuantity() {
        return stockQuantity;
    }
    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public ProductStatusEnum getStatus() {
        return status;
    }

    public void setStatus(ProductStatusEnum status) {
        this.status = status;
    }
    public List<OrderItemsEntity> getOrderItems() {
        return orderItems;
    }
    public void setOrderItems(List<OrderItemsEntity> orderItems) {
        this.orderItems = orderItems;
    }
    public List<CartItemEntity> getCartItemEntities() {
        return cartItemEntities;
    }
    public void setCartItemEntities(List<CartItemEntity> cartItemEntities) {
        this.cartItemEntities = cartItemEntities;
    }
}
