package com.example.demo.exception;

import com.example.demo.enums.ErrorCodeEnum;

public class InvalidCredentialsHandler extends RuntimeException {
    private ErrorCodeEnum errorCodeEnum;
    public  InvalidCredentialsHandler(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMessage());
        this.errorCodeEnum = errorCodeEnum;
    }
    public ErrorCodeEnum getErrorCodeEnum() {
        return errorCodeEnum;
    }
}
