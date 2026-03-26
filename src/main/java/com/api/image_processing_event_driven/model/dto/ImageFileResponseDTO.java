package com.api.image_processing_event_driven.model.dto;

public record ImageFileResponseDTO(
        byte[] data,
        String contentType
) {}
