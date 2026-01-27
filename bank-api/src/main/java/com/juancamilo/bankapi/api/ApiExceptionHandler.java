package com.juancamilo.bankapi.api;

import com.juancamilo.bankapi.api.dto.ErrorResponse;
import domain.exception.InsufficientFundsException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorResponse> handleInsufficientFunds(InsufficientFundsException ex) {
        return ResponseEntity.status(409).body(
                new ErrorResponse("INSUFFICIENT_FUNDS", ex.getMessage())
        );
    }
}