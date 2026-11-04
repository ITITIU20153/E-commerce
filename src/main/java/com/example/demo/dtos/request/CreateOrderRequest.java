package com.example.demo.dtos.request;

import java.util.List;

import com.example.demo.enums.OrderStatusEnum;

public class CreateOrderRequest {
    private Long userId;
    private String note;
    private List<OrderItemsRequest> OrderItemsRequests;
    
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public List<OrderItemsRequest> getOrderItemsRequests() {
        return OrderItemsRequests;
    }
    public void setOrderItemsRequests(List<OrderItemsRequest> orderItemsRequests) {
        OrderItemsRequests = orderItemsRequests;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    
}
