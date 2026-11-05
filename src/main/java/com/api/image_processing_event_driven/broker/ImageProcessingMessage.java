package com.api.image_processing_event_driven.broker;

public record ImageProcessingMessage(
        String imageId,
        String processedImageId
) {
}