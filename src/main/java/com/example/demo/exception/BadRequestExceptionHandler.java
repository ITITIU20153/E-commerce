package com.example.demo.exception;

import com.example.demo.enums.ErrorCodeEnum;

public class BadRequestExceptionHandler extends RuntimeException {
    private ErrorCodeEnum errorCodeEnum;
    public ErrorCodeEnum getErrorCodeEnum() {
        return errorCodeEnum;
    }
    public  BadRequestExceptionHandler(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMessage());
        this.errorCodeEnum = errorCodeEnum;
    }
}
