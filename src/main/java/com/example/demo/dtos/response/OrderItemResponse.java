package com.example.demo.dtos.response;

import java.math.BigDecimal;

public class OrderItemResponse {
    private String productName;
    private BigDecimal price;
    private Integer quantity;
    
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
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    } 
}
