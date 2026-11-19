package com.imageprocessing.api.service;

import com.imageprocessing.api.model.dto.RegisterRequest;
import com.imageprocessing.api.model.dto.TokenRequest;
import com.imageprocessing.api.model.dto.TokenResponse;

public interface AuthService {
    TokenResponse register(RegisterRequest request);

    TokenResponse login(TokenRequest request);

    TokenResponse refreshToken(String authentication);
}
