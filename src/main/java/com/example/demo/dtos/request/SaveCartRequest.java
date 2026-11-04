package com.example.demo.dtos.request;

import java.util.List;

public class SaveCartRequest {
    private Long cartId;
    private Long userId;
    private List<AddToCartRequest> AddToCartRequest;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public Long getCartId() {
        return cartId;
    }

    public void setCartId(Long cartId) {
        this.cartId = cartId;
    }

    public List<AddToCartRequest> getAddToCartRequest() {
        return AddToCartRequest;
    }

    public void setAddToCartRequest(List<AddToCartRequest> addToCartRequest) {
        AddToCartRequest = addToCartRequest;
    }    
}
