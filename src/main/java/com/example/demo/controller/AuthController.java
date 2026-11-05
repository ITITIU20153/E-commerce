package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.request.LoginRequest;
import com.example.demo.dtos.request.RegisterRequest;
import com.example.demo.dtos.response.LoginResponse;
import com.example.demo.dtos.response.RegisterResponse;
import com.example.demo.service.UserService;

@RestController 
@RequestMapping("/auth")
public class AuthController {
    private UserService userService;
    public  AuthController(
        UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> saveUsers(@RequestBody RegisterRequest userRegister) {
        RegisterResponse response = userService.saveUser(userRegister);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest userLogin) {
        LoginResponse response = userService.login(userLogin);
        return ResponseEntity.ok(response);
    }
}
