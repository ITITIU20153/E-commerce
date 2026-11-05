package com.example.demo.dtos.response;

import com.example.demo.enums.UserRoleEnum;

public class UserResponse {
    private String username;
    private UserRoleEnum role;
    private CartResponse cart;
    public CartResponse getCart() {
        return cart;
    }
    public void setCart(CartResponse cart) {
        this.cart = cart;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public UserRoleEnum getRole() {
        return role;
    }
    public void setRole(UserRoleEnum role) {
        this.role = role;
    }
}
