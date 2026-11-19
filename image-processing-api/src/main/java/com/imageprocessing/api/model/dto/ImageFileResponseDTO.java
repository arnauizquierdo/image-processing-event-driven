package com.imageprocessing.api.model.dto;

public record ImageFileResponseDTO(
        byte[] data,
        String contentType
) {}
