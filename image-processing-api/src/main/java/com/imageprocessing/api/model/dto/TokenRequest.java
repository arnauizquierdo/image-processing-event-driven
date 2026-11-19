package com.imageprocessing.api.model.dto;

public record TokenRequest (
    String username,
    String password
) {}
