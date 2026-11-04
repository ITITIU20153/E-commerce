package com.example.demo.dtos.request;

import com.example.demo.enums.UserRoleEnum;

import jakarta.validation.constraints.NotNull;

public class RegisterRequest {
    @NotNull(message = "username is require")
    private String username;
    @NotNull(message = "password is require")
    private String password;
    @NotNull(message = "role is require")
    private  UserRoleEnum role;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public UserRoleEnum getRole() {
        return role;
    }
    public void setRole(UserRoleEnum role) {
        this.role = role;
    }
}
