package com.example.demo.dtos.response;

import java.math.BigDecimal;
import java.util.List;

import com.example.demo.enums.OrderStatusEnum;

public class OrderResponse {
    private String username;
    private OrderStatusEnum status;
    private BigDecimal totalAmount;
    private Integer totalQuantity;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
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
    private String note;
    private List<OrderItemResponse> OrderItemResponses;
    
    public OrderStatusEnum getStatus() {
        return status;
    }
    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public List<OrderItemResponse> getOrderItemResponses() {
        return OrderItemResponses;
    }
    public void setOrderItemResponses(List<OrderItemResponse> orderItemResponses) {
        OrderItemResponses = orderItemResponses;
    }
}
