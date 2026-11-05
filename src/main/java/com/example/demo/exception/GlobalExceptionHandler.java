package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.dtos.errors.ErrorResponse;
import com.example.demo.enums.ErrorCodeEnum;

/**
 * GlobalException
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

        @ExceptionHandler(NotFoundException.class)
        public ResponseEntity<ErrorResponse> handleNotFound(
                NotFoundException ex) {

            ErrorCodeEnum errorCode = ex.getErrorCodeEnum();
            
            return ResponseEntity
                    .status(errorCode.getStatus().value())
                    .body(toErrorResponse(errorCode));
        }

        @ExceptionHandler(UsernameExistsException.class)
        public ResponseEntity<ErrorResponse> handleUsernameExistException(
                UsernameExistsException ex) {
            ErrorCodeEnum e = ex.getErrorCodeEnum();
            return ResponseEntity
                    .status(e.getStatus().value())
                    .body(toErrorResponse(e));
        }

        @ExceptionHandler(InvalidCredentialsHandler.class)
        public ResponseEntity<ErrorResponse> handleInvalidCredenttialException(
                InvalidCredentialsHandler ex) {
            ErrorCodeEnum e = ex.getErrorCodeEnum();
            return ResponseEntity
                    .status(e.getStatus().value())
                    .body(toErrorResponse(e));
        }

        @ExceptionHandler (BadRequestExceptionHandler.class) 
        public ResponseEntity<ErrorResponse> handleBadRequestException(
                BadRequestExceptionHandler ex) {
            ErrorCodeEnum e = ex.getErrorCodeEnum();
            return ResponseEntity
                    .status(e.getStatus().value())
                    .body(toErrorResponse(e));
        }

        private ErrorResponse toErrorResponse(ErrorCodeEnum e) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setCode(e.getCode());
            errorResponse.setMessage(e.getMessage());
            errorResponse.setStatus(e.getStatus().value());
            return errorResponse;
        }
}