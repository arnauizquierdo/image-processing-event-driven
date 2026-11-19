package com.imageprocessing.api.model.dto;

import com.imageprocessing.core.entity.ProcessingStatus;

import java.time.LocalDateTime;

public record ProcessImageResponseDTO(
        String processedImageId,
        ProcessingStatus status,
        LocalDateTime createdAt
) {}
