package com.example.demo.dtos.errors;

public class ErrorResponse {

    private String code;
    private String message;
    private int status;
    public  ErrorResponse() {}
    public ErrorResponse(
            String code,
            String message,
            int status) {

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

    public int getStatus() {
        return status;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}