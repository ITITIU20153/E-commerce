package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.request.SaveCartRequest;
import com.example.demo.dtos.response.CartResponse;
import com.example.demo.service.CartService;


@RestController 
@RequestMapping("/cart")
public class CartController {
    private CartService cartService;
    CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartResponse> findCartByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.findCartByUserId(userId));
    }

    @PostMapping() 
    public ResponseEntity<Void> save(@RequestBody SaveCartRequest request) {
        System.out.println("Request: " + request);
    // System.out.println("User ID: " + request.getUserId());
    // System.out.println("Cart Items: " + request.getAddToCartRequest());
        cartService.addItemsToCart(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
