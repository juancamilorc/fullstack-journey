package com.juancamilo.bankapi.api.dto;

import java.time.Instant;

public record MovementResponse(
        Instant occurredAt,
        String type,
        long amount,
        long resultingBalance
) {}