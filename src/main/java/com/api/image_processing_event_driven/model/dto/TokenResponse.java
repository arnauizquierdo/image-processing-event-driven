package com.api.image_processing_event_driven.model.dto;

public record TokenResponse(
   String token,
   String refreshToken
) {}
