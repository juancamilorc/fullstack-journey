package com.juancamilo.bankapi.api;

import com.juancamilo.bankapi.api.dto.ApiError;
import com.juancamilo.bankapi.api.dto.ErrorResponse;
import domain.exception.InsufficientFundsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(domain.exception.InsufficientFundsException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientFunds(domain.exception.InsufficientFundsException ex) {
        return ResponseEntity.status(409).body(
                new ErrorResponse("INSUFFICIENT_FUNDS", ex.getMessage())
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleBadRequest(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(
                new ErrorResponse("BAD_REQUEST", ex.getMessage())
        );
    }

    @RestControllerAdvice
    public static class GlobalExceptionHandler {

        @ExceptionHandler(InsufficientFundsException.class)
        public ResponseEntity<ApiError> handleInsufficientFunds(InsufficientFundsException ex) {
            return ResponseEntity.status(409).body(
                    new ApiError("INSUFFICIENT_FUNDS", ex.getMessage())
            );
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<ApiError> handleIllegalArgument(IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(
                    new ApiError("BAD_REQUEST", ex.getMessage())
            );
        }
    }
}