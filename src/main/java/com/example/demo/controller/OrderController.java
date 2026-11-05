package com.example.demo.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/{id}")
    public  ResponseEntity<List<OrderResponse>> findOrdersByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findOrdersByUserId(id));
    }

    @PostMapping("/{id}")
    public ResponseEntity<OrderResponse> cancelOrder(
        @PathVariable Long id){
        OrderResponse orderResponse = orderService.cancelOrder(id);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
    }

    @PostMapping()
    public ResponseEntity<OrderResponse> saveOrder(
        @RequestBody  CreateOrderRequest requests){
        OrderResponse orderResponse = orderService.saveOrder(requests);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderResponse);
    }
}
