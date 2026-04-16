package com.api.image_processing_event_driven.model.dto;

public record ProcessImageRequestDTO(
        String imageId,
        String watermarkText
) {}
