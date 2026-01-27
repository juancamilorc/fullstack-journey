package com.juancamilo.bankapi.api.dto;

public record ErrorResponse(
        String code,
        String message
) {}