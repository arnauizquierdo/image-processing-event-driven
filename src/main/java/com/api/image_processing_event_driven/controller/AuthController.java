package com.api.image_processing_event_driven.controller;

import com.api.image_processing_event_driven.model.dto.RegisterRequest;
import com.api.image_processing_event_driven.model.dto.TokenRequest;
import com.api.image_processing_event_driven.model.dto.TokenResponse;
import com.api.image_processing_event_driven.service.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody RegisterRequest request) {
        final TokenResponse response = service.register(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody TokenRequest request) {
        final TokenResponse response = service.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<TokenResponse> refreshToken(@RequestHeader(HttpHeaders.AUTHORIZATION) final String authentication) {
        final TokenResponse response = service.refreshToken(authentication);
        return ResponseEntity.ok(response);
    }

}
