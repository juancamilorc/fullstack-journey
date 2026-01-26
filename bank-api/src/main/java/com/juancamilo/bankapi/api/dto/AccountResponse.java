package com.juancamilo.bankapi.api.dto;

public record AccountResponse(
        String number,
        String type,
        long balanceAmount,
        String ownerId
) {}
