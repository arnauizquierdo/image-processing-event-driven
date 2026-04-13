package com.api.image_processing_event_driven.model.dto;

public record TokenRequest (
    String username,
    String password
) {}
