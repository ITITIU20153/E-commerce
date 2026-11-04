package com.example.demo.dtos.request;

import com.example.demo.enums.OrderStatusEnum;

public class OrderRequest {
    private OrderStatusEnum status;
    public OrderStatusEnum getStatus() {
        return status;
    }
    public void setStatus(OrderStatusEnum status) {
        this.status = status;
    }
}
