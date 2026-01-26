package com.juancamilo.bankapi.api.dto;

public record CreateAccountRequest(
        String number,
        String type
) {}