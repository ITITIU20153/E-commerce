package com.example.demo.dtos.request;

import jakarta.validation.constraints.NotNull;

public class LoginRequest {
    @NotNull(message = "username is require")
    private String username;
    @NotNull(message = "password is require")
    private String password;
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
}
