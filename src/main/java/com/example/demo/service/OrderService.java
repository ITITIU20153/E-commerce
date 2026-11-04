package com.example.demo.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dtos.request.CreateOrderRequest;
import com.example.demo.dtos.request.OrderItemsRequest;
import com.example.demo.dtos.response.OrderItemResponse;
import com.example.demo.dtos.response.OrderResponse;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.OrderItemsEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.enums.ErrorCodeEnum;
import com.example.demo.enums.OrderStatusEnum;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.OrderRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;

@Service 
public class OrderService {
    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private UserRepository userRepository;
    
    OrderService(OrderRepository orderRepository, 
        ProductRepository productRepository, 
        UserRepository userRepository) {

        this.orderRepository=orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }
    @Transactional 
    public OrderResponse saveOrder(CreateOrderRequest orderRequests) {
      OrderEntity order = toOrderEntity(orderRequests);
      Integer totalQuantity = 0;
      BigDecimal totalAmount = new BigDecimal(0);        
        List<OrderItemsEntity> orderItemsEntities = new ArrayList<>();
        for (OrderItemsRequest itemRequest: orderRequests.getOrderItemsRequests()) {
            OrderItemsEntity item = toOrderItemEntity(itemRequest);
            BigDecimal price = item.getProductEntity().getPrice();
            totalQuantity = totalQuantity + item.getQuantity();
            totalAmount= totalAmount.add(price.multiply(BigDecimal.valueOf(item.getQuantity())));
            orderItemsEntities.add(item);
            item.setOrderEntity(order);
        }    
        order.setTotalAmount(totalAmount);
        order.setTotalQuantity(totalQuantity);
        order.setOrderItemsEntity(orderItemsEntities);
        order.setStatus(OrderStatusEnum.PENDING);
        return toResponse(orderRepository.save(order));
    }
    private OrderResponse toResponse(OrderEntity orderEntity) {
        OrderResponse reponse = new OrderResponse();
        reponse.setStatus(orderEntity.getStatus());
        reponse.setNote(orderEntity.getNote());
        reponse.setUsername(orderEntity.getUserEntity().getUsername());
        reponse.setTotalAmount(orderEntity.getTotalAmount());
        reponse.setTotalQuantity(orderEntity.getTotalQuantity());
        reponse.setOrderItemResponses(null);
        List<OrderItemResponse> itemReponses = new ArrayList<>();
        for (OrderItemsEntity item: orderEntity.getOrderItemsEntity()) {
            OrderItemResponse itemReponse = new OrderItemResponse();
            itemReponse.setProductName(item.getProductEntity().getProductName());
            itemReponse.setPrice(item.getProductEntity().getPrice());
            itemReponse.setQuantity(item.getQuantity());
            itemReponses.add(itemReponse);
        }        
        reponse.setOrderItemResponses(itemReponses);
        return  reponse;
    }  
    private OrderEntity toOrderEntity(CreateOrderRequest orderRequest) {
        UserEntity user = userRepository.findById(orderRequest.getUserId()).
        orElseThrow(() -> new NotFoundException(ErrorCodeEnum.USER_NOT_FOUND));
        OrderEntity order = new OrderEntity();
        order.setUserEntity(user);
        order.setNote(orderRequest.getNote());
        return order;
    }
    private OrderItemsEntity toOrderItemEntity(OrderItemsRequest orderItemRequest) {
        ProductEntity product = productRepository.findById(orderItemRequest.getProductId()).
        orElseThrow(() -> new NotFoundException(ErrorCodeEnum.PRODUCT_NOT_FOUND));
        Integer quantity = orderItemRequest.getQuantity();
        if (quantity == null || quantity <= 0) {
            throw new RuntimeException("Invalid quantity");
        }

        if (product.getStockQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }
        OrderItemsEntity item = new OrderItemsEntity();
        item.setProductEntity(product);
        item.setQuantity(orderItemRequest.getQuantity());
        return item;
    }
}
