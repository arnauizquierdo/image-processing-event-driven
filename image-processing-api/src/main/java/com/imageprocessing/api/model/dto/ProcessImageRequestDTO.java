package com.imageprocessing.api.model.dto;

public record ProcessImageRequestDTO(
        String imageId,
        String watermarkText
) {}
