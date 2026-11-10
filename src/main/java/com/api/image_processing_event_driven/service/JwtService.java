package com.api.image_processing_event_driven.service;

import com.api.image_processing_event_driven.model.entity.User;

public interface JwtService {
    String extractUsername(String token);

    String generateToken(User user);

    String generateRefreshToken(User user);

    boolean isTokenValid(String token, User user);

    String extractTokenType(String token);
}
