package com.juancamilo.bankapi.api.dto;

public record MovementResponse(
        String occurredAt,
        String type,
        long amount,
        long resultingBalance
) {}