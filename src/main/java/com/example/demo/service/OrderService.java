package com.example.demo.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dtos.request.OrderItemsRequest;
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
    public OrderEntity saveOrder(Long userId, List<OrderItemsRequest> itemsRequests) {
      
    OrderEntity order = new OrderEntity();
      UserEntity user = userRepository.findById(userId).
      orElseThrow(() -> new NotFoundException(ErrorCodeEnum.USER_NOT_FOUND));
        BigDecimal subTotal = new BigDecimal(0);
      order.setUserEntity(user);
      order.setStatus(OrderStatusEnum.PENDING);
      Integer sum = 0;
      List<OrderItemsEntity> items = new ArrayList<>(); 
        
      for (OrderItemsRequest itemRequest: itemsRequests) {
            OrderItemsEntity item = new OrderItemsEntity();
            ProductEntity product = productRepository.findById(itemRequest.getProductId()).
            orElseThrow(() -> new NotFoundException(ErrorCodeEnum.PRODUCT_NOT_FOUND));
            
            Integer quantity = itemRequest.getQuantity();

            if (quantity == null || quantity <= 0) {
                throw new RuntimeException("Invalid quantity");
            }

            if (product.getStockQuantity() < quantity) {
                throw new RuntimeException("Insufficient stock");
            }
            sum = sum + quantity;
            item.setProductEntity(product);
            item.setQuantity(quantity);
            item.setOrderEntity(order);
            items.add(item);
            subTotal = subTotal .add(product.getPrice().multiply(BigDecimal.valueOf(itemRequest.getQuantity())));
        }
        order.setSubTotal(subTotal);
        order.setOrderItemsEntity(items);
        order.setQuantity(sum);
        
        return orderRepository.save(order);
    }  
}
