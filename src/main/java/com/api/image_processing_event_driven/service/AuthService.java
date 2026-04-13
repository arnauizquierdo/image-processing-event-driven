package com.api.image_processing_event_driven.service;

import com.api.image_processing_event_driven.model.dto.RegisterRequest;
import com.api.image_processing_event_driven.model.dto.TokenRequest;
import com.api.image_processing_event_driven.model.dto.TokenResponse;
import com.api.image_processing_event_driven.model.entity.Role;
import com.api.image_processing_event_driven.model.entity.User;
import com.api.image_processing_event_driven.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository repository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public TokenResponse register(final RegisterRequest request) {
        User user = new User(request.username(), request.email(), passwordEncoder.encode(request.password()), Role.ROLE_USER);
        repository.save(user);
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return new TokenResponse(accessToken, refreshToken);
    }

    public TokenResponse login(final TokenRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        User user = repository.findByUsername(request.username()).orElseThrow();
        String accessToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return new TokenResponse(accessToken, refreshToken);
    }

    public TokenResponse refreshToken(final String authentication) {
        if (authentication == null || !authentication.startsWith("Bearer ")) {
            throw new IllegalArgumentException("No valid HEADER");
        }
        String refreshToken = authentication.substring(7);
        if (!"refresh".equals(jwtService.extractTokenType(refreshToken))) {
            throw new RuntimeException("Invalid token type");
        }
        String username = jwtService.extractUsername(refreshToken);
        if (username == null) {
            throw new RuntimeException("Username in token invalid");
        }
        final User user = this.repository.findByUsername(username).orElseThrow();
        if (!jwtService.isTokenValid(refreshToken, user)) throw new RuntimeException("Token not valid.");
        final String accessToken = jwtService.generateToken(user);
        return new TokenResponse(accessToken, refreshToken);
    }



}
