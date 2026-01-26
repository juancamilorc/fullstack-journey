package com.juancamilo.bankapi.api.dto;

public record ClientResponse(
        String id,
        String name,
        String document
) {}