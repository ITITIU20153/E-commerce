package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.request.CreateOrderRequest;
import com.example.demo.dtos.response.OrderResponse;
import com.example.demo.service.OrderService;

@RestController 
@RequestMapping("/orders")
public class OrderController {
    private OrderService orderService;
    OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping()
    public ResponseEntity<OrderResponse> saveOrder(
        @RequestBody  CreateOrderRequest requests){
        OrderResponse orderResponse = orderService.saveOrder(requests);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
    }
}
