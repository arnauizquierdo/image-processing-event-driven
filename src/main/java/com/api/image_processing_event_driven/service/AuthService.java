package com.api.image_processing_event_driven.service;

import com.api.image_processing_event_driven.model.dto.RegisterRequest;
import com.api.image_processing_event_driven.model.dto.TokenRequest;
import com.api.image_processing_event_driven.model.dto.TokenResponse;

public interface AuthService {
    TokenResponse register(RegisterRequest request);

    TokenResponse login(TokenRequest request);

    TokenResponse refreshToken(String authentication);
}
