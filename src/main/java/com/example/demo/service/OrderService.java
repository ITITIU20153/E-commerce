package com.example.demo.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dtos.request.CreateOrderRequest;
import com.example.demo.dtos.request.OrderItemsRequest;
import com.example.demo.dtos.request.UpdateCartItemRequest;
import com.example.demo.dtos.response.OrderItemResponse;
import com.example.demo.dtos.response.OrderResponse;
import com.example.demo.entity.CartEntity;
import com.example.demo.entity.CartItemEntity;
import com.example.demo.entity.OrderEntity;
import com.example.demo.entity.OrderItemsEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.enums.ErrorCodeEnum;
import com.example.demo.enums.OrderStatusEnum;
import com.example.demo.enums.ProductStatusEnum;
import com.example.demo.exception.BadRequestExceptionHandler;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.CartRepository;
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

        this.orderRepository= orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }
    public List<OrderResponse> findOrdersByUserId(Long id) {
        List<OrderEntity> orders = orderRepository.findOrderByUserEntity_Id(id);
        List<OrderResponse> orderResponses = new ArrayList<>();
        for (OrderEntity order: orders) {
            orderResponses.add(toResponse(order));
        }
        return orderResponses;
    }

    public OrderResponse cancelOrder(Long orderId) {
        OrderEntity order = orderRepository.findById(orderId).
        orElseThrow(() -> new NotFoundException(ErrorCodeEnum.ORDER_NOT_FOUND));
        order.getOrderItemsEntity().forEach(item -> returnProduct(item)); 
        order.setStatus(OrderStatusEnum.CANCELLED);
        return toResponse(order);
    }
    @Transactional 
    public OrderResponse saveOrder(CreateOrderRequest orderRequests) {
      OrderEntity order = toOrderEntity(orderRequests);
      Integer totalQuantity = 0;
      BigDecimal totalAmount = new BigDecimal(0);        
        List<OrderItemsEntity> orderItemsEntities = new ArrayList<>();
        // CartEntity cart =cartRepository.findByUserId(order.getUserEntity().getId()).
        // orElseThrow(() -> new NotFoundException(ErrorCodeEnum.CART_NOT_FOUND));
        // List<CartItemEntity> cartItems = cart.getCartItemEntities();

        for (OrderItemsRequest itemRequest: orderRequests.getOrderItemsRequests()) {
            OrderItemsEntity item = toOrderItemEntity(itemRequest);
            // decrease Product
            decreaseProduct(item);
            //
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
        OrderResponse orderResponse = toResponse(orderRepository.save(order));
        
        return orderResponse;
    }

    private OrderResponse toResponse(OrderEntity orderEntity) {
        OrderResponse reponse = new OrderResponse();
        reponse.setStatus(orderEntity.getStatus());
        reponse.setNote(orderEntity.getNote());
        reponse.setUsername(orderEntity.getUserEntity().getUsername());
        reponse.setTotalAmount(orderEntity.getTotalAmount());
        reponse.setTotalQuantity(orderEntity.getTotalQuantity());
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
            throw new BadRequestExceptionHandler(ErrorCodeEnum.INVALID_ORDER);
        }
        OrderItemsEntity item = new OrderItemsEntity();
        item.setProductEntity(product);
        item.setQuantity(orderItemRequest.getQuantity());
        return item;
    }
    private void decreaseProduct(OrderItemsEntity item) {
        ProductEntity product = item.getProductEntity();
            Integer remainStockQuantity = product.getStockQuantity() - item.getQuantity();
            product.setStockQuantity(remainStockQuantity);
            if (product.getStatus() == ProductStatusEnum.INACTIVE) {
                throw new BadRequestExceptionHandler(ErrorCodeEnum.INACTIVE_PRODUCT);
            }
            if (remainStockQuantity < 0 ) {
                throw new BadRequestExceptionHandler(ErrorCodeEnum.INVALID_ORDER);
            }
            if (remainStockQuantity == 0) {
                product.setStatus(ProductStatusEnum.OUT_OF_STOCK);
            }
            productRepository.save(product);
    }
    private void returnProduct(OrderItemsEntity item) {
        ProductEntity product = item.getProductEntity();
        if (product.getStatus() == ProductStatusEnum.OUT_OF_STOCK) {
            product.setStatus(ProductStatusEnum.ACTIVE);
        } 
        Integer stockQuantity = product.getStockQuantity() + item.getQuantity();
        product.setStockQuantity(stockQuantity);
        productRepository.save(product);
    }
}
