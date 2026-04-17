package com.api.image_processing_event_driven.model.dto;

import java.time.LocalDateTime;

public record UploadResponseDTO(
        String id,
        String username,
        String originalFileName,
        Long fileSize,
        String contentType,
        LocalDateTime createdAt
) {}
