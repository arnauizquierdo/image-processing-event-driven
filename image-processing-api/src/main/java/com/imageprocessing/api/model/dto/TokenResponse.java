package com.imageprocessing.api.model.dto;

public record TokenResponse(
   String token,
   String refreshToken
) {}
