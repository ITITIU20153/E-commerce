package com.example.demo.dtos.response;

import java.math.BigDecimal;

import com.example.demo.enums.ProductStatusEnum;

public class CartItemResponse {
    private Long productId;
    private String productName;
    private BigDecimal price;
    private Integer stockQuantity;
    private Integer quantity;
    private ProductStatusEnum status;
    public Long getProductId() {
        return productId;
    }
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
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
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public ProductStatusEnum getStatus() {
        return status;
    }
    public void setStatus(ProductStatusEnum status) {
        this.status = status;
    }

}
