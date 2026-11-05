package com.example.demo.dtos.request;

import java.util.List;

public class ModifyCartRequest {
    private Long userId;
    private List<UpdateCartItemRequest> updateCartItemRequests;

    public List<UpdateCartItemRequest> getUpdateCartItemRequests() {
        return updateCartItemRequests;
    }

    public void setUpdateCartItemRequests(List<UpdateCartItemRequest> updateCartItemRequests) {
        this.updateCartItemRequests = updateCartItemRequests;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        System.out.println("Setter userId: " + userId);
        this.userId = userId;
    }
}
