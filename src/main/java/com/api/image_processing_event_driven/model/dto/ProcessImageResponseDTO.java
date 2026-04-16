package com.api.image_processing_event_driven.model.dto;

import com.api.image_processing_event_driven.model.entity.ProcessingStatus;

import java.time.LocalDateTime;

public record ProcessImageResponseDTO(
        String processedImageId,
        ProcessingStatus status,
        LocalDateTime createdAt
) {}
