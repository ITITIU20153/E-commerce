package com.example.demo.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dtos.request.AddToCartRequest;
import com.example.demo.dtos.request.SaveCartRequest;
import com.example.demo.dtos.response.CartItemResponse;
import com.example.demo.dtos.response.CartResponse;
import com.example.demo.entity.CartEntity;
import com.example.demo.entity.CartItemEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.enums.ErrorCodeEnum;
import com.example.demo.exception.NotFoundException;
import com.example.demo.repository.CartItemRepository;
import com.example.demo.repository.CartRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service 
public class CartService {
    private CartRepository cartRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;
    CartService(CartRepository cartRepository,
        UserRepository userRepository,
    ProductRepository productRepository) {
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }
    
    @Transactional 
    public CartEntity addItemsToCart(SaveCartRequest cartRequest) {
        CartEntity cart = toCartEntity(cartRequest);
        List<AddToCartRequest> itemRequests = cartRequest.getAddToCartRequest();
        List<CartItemEntity> items = new ArrayList<>();
        Integer quantity = 0;
        BigDecimal amount = new BigDecimal(0);
        for (AddToCartRequest item: itemRequests) {
            CartItemEntity cartItemEntity = toCartItemEntity(item);
            BigDecimal productPrice = cartItemEntity.getProductEntity().getPrice();
            cartItemEntity.setCartEntity(cart);
            quantity = quantity + cartItemEntity.getQuantity();
            amount = amount.add((productPrice.multiply(BigDecimal.valueOf(cartItemEntity.getQuantity()))));
            items.add(cartItemEntity);
        }
        cart.setTotalAmount(amount);
        cart.setTotalQuantity(quantity);
        cart.setCartItemEntities(items);
        return cartRepository.save(cart);
    }

    public CartResponse findCartByUserId(Long userId) {
        CartEntity cart = cartRepository.findByUserId(userId).
        orElseThrow(() -> new NotFoundException(ErrorCodeEnum.CART_NOT_FOUND));
        return toResponse(cart);
    }

    private CartResponse toResponse(CartEntity cart) {
        CartResponse cartResponse = new CartResponse();
        CartItemResponse cartItemResponse = new CartItemResponse();
        List<CartItemResponse> cartItemResponses =  new ArrayList<>();
        cartResponse.setTotalAmount(cart.getTotalAmount());
        cartResponse.setTotalQuantity(cart.getTotalQuantity());
        for (CartItemEntity item: cart.getCartItemEntities()) {
            ProductEntity product = item.getProductEntity();
            cartItemResponse.setPrice(product.getPrice());
            cartItemResponse.setProductId(product.getId());
            cartItemResponse.setProductName(product.getProductName());
            cartItemResponse.setQuantity(item.getQuantity());
            cartItemResponse.setStatus(product.getStatus());
            cartItemResponse.setStockQuantity(product.getStockQuantity());
            cartItemResponses.add(cartItemResponse);
        }
        cartResponse.setItems(cartItemResponses);
        return cartResponse;
    }
    private CartEntity toCartEntity(SaveCartRequest cartRequest) {
        System.out.println(cartRequest.getUserId());
        CartEntity cart = cartRepository.findByUserId(cartRequest.getUserId()).
        orElseThrow(() -> new NotFoundException(ErrorCodeEnum.CART_NOT_FOUND));
        UserEntity user = userRepository.findById(cartRequest.getUserId()).
        orElseThrow(() -> new NotFoundException(ErrorCodeEnum.USER_NOT_FOUND));
        cart.setUser(user);
        return cart;
    }
    private CartItemEntity toCartItemEntity(AddToCartRequest itemRequest) {
        CartItemEntity item = new CartItemEntity();
        item.setQuantity(itemRequest.getQuantity());
        ProductEntity product = productRepository.findById(itemRequest.getProductId()).
        orElseThrow(() -> new NotFoundException(ErrorCodeEnum.PRODUCT_NOT_FOUND));
        if (item.getQuantity() == null || item.getQuantity() <0) {
                throw new RuntimeException("Invalid quantity");
        }
        if (item.getQuantity() > product.getStockQuantity()) {
                throw new RuntimeException("Insufficient stock");
        }
        item.setProductEntity(product);
        return item;
    }
}
