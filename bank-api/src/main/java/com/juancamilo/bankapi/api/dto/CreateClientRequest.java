package com.juancamilo.bankapi.api.dto;

public record CreateClientRequest(
        String id,
        String name,
        String document
) {}
