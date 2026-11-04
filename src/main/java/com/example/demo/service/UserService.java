package com.example.demo.service;

import java.math.BigDecimal;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.request.LoginRequest;
import com.example.demo.dtos.request.RegisterRequest;
import com.example.demo.dtos.response.LoginResponse;
import com.example.demo.entity.CartEntity;
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

    @Transactional 
    public UserEntity saveUser(RegisterRequest registerRequest) {
        existByUsername(registerRequest.getUsername());
        UserEntity user = toEntity(registerRequest);
        CartEntity cart = new CartEntity();
        cart.setCartItemEntities(null);
        cart.setTotalAmount(BigDecimal.ZERO);
        cart.setTotalQuantity(0);
        cart.setUser(user);
        user.setCartEntity(cart);
        return userRepository.save(user);
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
}
