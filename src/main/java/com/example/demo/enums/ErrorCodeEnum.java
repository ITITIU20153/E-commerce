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
