package com.example.demo.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.request.LoginRequest;
import com.example.demo.dtos.request.RegisterRequest;
import com.example.demo.dtos.response.CartItemResponse;
import com.example.demo.dtos.response.CartResponse;
import com.example.demo.dtos.response.LoginResponse;
import com.example.demo.dtos.response.RegisterResponse;
import com.example.demo.dtos.response.UserResponse;
import com.example.demo.entity.CartEntity;
import com.example.demo.entity.CartItemEntity;
import com.example.demo.entity.ProductEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.enums.ErrorCodeEnum;
import com.example.demo.exception.InvalidCredentialsHandler;
import com.example.demo.exception.NotFoundException;
import com.example.demo.exception.UsernameExistsException;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenProvider;

import jakarta.transaction.Transactional;

@Service 
public class UserService {
    private UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    //register
    @Transactional 
    public RegisterResponse saveUser(RegisterRequest registerRequest) {
        existByUsername(registerRequest.getUsername());
        UserEntity user = toEntity(registerRequest);
        CartEntity cart = new CartEntity();
        cart.setCartItemEntities(null);
        cart.setTotalAmount(BigDecimal.ZERO);
        cart.setTotalQuantity(0);
        cart.setUser(user);
        user.setCartEntity(cart);
        userRepository.save(user);
        RegisterResponse response = new RegisterResponse();
        response.setHttpCode(HttpStatus.CREATED);
        response.setMess("Register Successfully");
        return response;
    }
    public void deleteUserById(Long id) {
        UserEntity user = existUserById(id);
        user.getOrderEntities().forEach(order -> order.setUserEntity(null));
        userRepository.delete(user);
    }
    public UserResponse findUserById(Long id) {
        UserEntity user = existUserById(id);
        return toUserReponse(user);    
    }
    public LoginResponse login(LoginRequest loginRequest) {
        UserEntity user = getUserByUsername(loginRequest.getUsername());
        String password = loginRequest.getPassword();
        String encodedPassword = user.getPassword();
        if (!passwordEncoder.matches(password, encodedPassword)) {
            throw new InvalidCredentialsHandler(ErrorCodeEnum.INVALID_CREDENTIALS);
        } 
        String token = jwtTokenProvider.generateToken(user);
            return new LoginResponse(
                "login successfully",
                token
            );
    }

    private void existByUsername(String username) {
        if (userRepository.existsByUsername(username)) {
                    throw new UsernameExistsException(ErrorCodeEnum.USERNAME_EXISTS);
        }
    }
    private UserEntity toEntity(RegisterRequest registerRequest) {
        return  new UserEntity(
            registerRequest.getUsername(),
            passwordEncoder.encode(registerRequest.getPassword()),
            registerRequest.getRole()
        );
    }
    private UserEntity getUserByUsername(String username) {
        UserEntity user = userRepository.findByUsername(username).orElseThrow(
            () -> new NotFoundException(ErrorCodeEnum.USER_NOT_FOUND));
        return user;  
    }
    private  UserEntity existUserById(Long id) {
        UserEntity user = userRepository.findById(id).
            orElseThrow(() -> new NotFoundException(ErrorCodeEnum.USER_NOT_FOUND));
            return  user;
    }
    private UserResponse toUserReponse(UserEntity user) {
        UserResponse response = new UserResponse();
        response.setUsername(user.getUsername());
        response.setRole(user.getRole());
        response.setCart(toCartReponse(user.getCartEntity()));
        return response;
    }

    private CartResponse toCartReponse(CartEntity cart) {
        CartResponse cartReponse = new CartResponse();
        cartReponse.setTotalAmount(cart.getTotalAmount());
        cartReponse.setTotalQuantity(cart.getTotalQuantity());
        List<CartItemResponse> itemResponses = new ArrayList<>();
        for (CartItemEntity item: cart.getCartItemEntities()) {
            CartItemResponse itemResponse = new CartItemResponse();
            ProductEntity product = item.getProductEntity();
            itemResponse.setProductId(product.getId());
            itemResponse.setProductName(product.getProductName());
            itemResponse.setPrice(product.getPrice());
            itemResponse.setQuantity(item.getQuantity());
            itemResponse.setStatus(product.getStatus());
            itemResponse.setStockQuantity(product.getStockQuantity());
            itemResponses.add(itemResponse);
        }
        cartReponse.setItems(itemResponses);
        return cartReponse;
    }
}
