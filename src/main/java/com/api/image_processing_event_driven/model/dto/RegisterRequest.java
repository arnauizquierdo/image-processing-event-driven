package com.api.image_processing_event_driven.model.dto;

public record RegisterRequest (
        String username,
        String email,
        String password
) {}