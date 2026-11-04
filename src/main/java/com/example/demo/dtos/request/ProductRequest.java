package com.example.demo.dtos.request;

import java.math.BigDecimal;

import com.example.demo.enums.ProductStatusEnum;

public class ProductRequest {
    private String productName;
    private String productDescription;
    private BigDecimal price;
    private Integer stockQuantity;
    private ProductStatusEnum status;
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
}
