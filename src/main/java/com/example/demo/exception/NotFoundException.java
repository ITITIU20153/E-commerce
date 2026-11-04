package com.example.demo.exception;

import com.example.demo.enums.ErrorCodeEnum;

public class NotFoundException extends RuntimeException {
    private  ErrorCodeEnum errorCodeEnum;
    public NotFoundException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMessage());
        this.errorCodeEnum = errorCodeEnum;
    }
    public ErrorCodeEnum getErrorCodeEnum() {
        return errorCodeEnum;
    }
} 
