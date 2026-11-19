package com.imageprocessing.api.model.dto;

import java.time.LocalDateTime;

public record UploadResponseDTO(
        String id,
        String username,
        String originalFileName,
        Long fileSize,
        String contentType,
        LocalDateTime createdAt
) {}
