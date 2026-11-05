package com.example.demo.dtos.response;

import org.springframework.http.HttpStatus;

public class RegisterResponse {
    private HttpStatus httpCode;
    public HttpStatus getHttpCode() {
        return httpCode;
    }
    public void setHttpCode(HttpStatus httpCode) {
        this.httpCode = httpCode;
    }
    private String mess;
    
    public String getMess() {
        return mess;
    }
    public void setMess(String mess) {
        this.mess = mess;
    }     
}
