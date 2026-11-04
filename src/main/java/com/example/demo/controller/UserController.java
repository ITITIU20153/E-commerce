package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.request.LoginRequest;
import com.example.demo.dtos.request.RegisterRequest;
import com.example.demo.dtos.response.LoginResponse;
import com.example.demo.service.UserService;

@RestController 
@RequestMapping("/auth")
public class UserController {
    private UserService userService;
    public  UserController(
        UserService userService) {
        this.userService = userService;
    }
    @PostMapping("/register")
    public ResponseEntity<Void> saveUsers(@RequestBody RegisterRequest userRegister) {
        userService.saveUser(userRegister);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest userLogin) {
        LoginResponse response = userService.login(userLogin);
        return ResponseEntity.ok(response);
    }
}
