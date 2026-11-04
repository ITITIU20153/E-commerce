package com.example.demo.dtos.request;

import java.util.List;

public class SaveCartRequest {
    private Long userId;
    private List<AddToCartRequest> addToCartRequest;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        System.out.println("Setter userId: " + userId);
        this.userId = userId;
    }
    
    public List<AddToCartRequest> getAddToCartRequest() {
        return addToCartRequest;
    }

    public void setAddToCartRequest(List<AddToCartRequest> addToCartRequest) {
        this.addToCartRequest = addToCartRequest;
    }    
}
