package com.example.demo.exception;

import com.example.demo.enums.ErrorCodeEnum;

public class UsernameExistsException extends RuntimeException {
    private  ErrorCodeEnum errorCodeEnum;
    public UsernameExistsException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMessage());
        this.errorCodeEnum = errorCodeEnum;
    }
    public ErrorCodeEnum getErrorCodeEnum() {
        return errorCodeEnum;
    }
} 