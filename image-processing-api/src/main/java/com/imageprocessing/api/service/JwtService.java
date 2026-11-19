package com.imageprocessing.api.service;

import com.imageprocessing.api.model.entity.User;

public interface JwtService {
    String extractUsername(String token);

    String generateToken(User user);

    String generateRefreshToken(User user);

    boolean isTokenValid(String token, User user);

    String extractTokenType(String token);
}
