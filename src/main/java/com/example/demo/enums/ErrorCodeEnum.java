package com.example.demo.enums;

import org.springframework.http.HttpStatus;

public enum ErrorCodeEnum {

    USERNAME_EXISTS(
        "USERNAME_EXISTS",
        "Username already exists",
        HttpStatus.CONFLICT
    ),

    USER_NOT_FOUND(
        "USER_NOT_FOUND",
        "User not found",
        HttpStatus.NOT_FOUND
    ),

    MISSING_REQUIRED_FIELD(
        "MISSING_REQUIRED_FIELD",
        "Missing Required Field",
        HttpStatus.BAD_REQUEST
    ),

    PRODUCT_NOT_FOUND(
        "PRODUCT_NOT_FOUND",
        "Product not found",
        HttpStatus.NOT_FOUND
    ),

    CART_NOT_FOUND(
        "CART_NOT_FOUND",
        "Cart not found",
        HttpStatus.NOT_FOUND
    ),

    ORDER_NOT_FOUND(
        "ORDER_NOT_FOUND",
        "Order not found",
        HttpStatus.NOT_FOUND
    ),

    INVALID_ORDER(
        "INVALID_ORDER",
        "Invalid Order",
        HttpStatus.BAD_REQUEST
    ),

    OUT_OF_STOCK_PRODUCT (
        "OUT_OF_STOCK_PRODUCT",
        "Product is out of stock",
        HttpStatus.BAD_REQUEST
    ),

    ITEM_NOT_FOUND (
        "ITEM_NOT_FOUND",
        "Not found item",
        HttpStatus.NOT_FOUND
    ),

    INACTIVE_PRODUCT (
        "INACTIVEK_PRODUCT",
        "Product is inactive",
        HttpStatus.BAD_REQUEST
    ),

    INVALID_CREDENTIALS(
        "INVALID_CREDENTIALS",
        "Username or password is incorrect",
        HttpStatus.UNAUTHORIZED
    );

    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCodeEnum(
            String code,
            String message,
            HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
