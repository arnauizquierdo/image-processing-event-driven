package com.imageprocessing.api.model.dto;

public record RegisterRequest (
        String username,
        String email,
        String password
) {}