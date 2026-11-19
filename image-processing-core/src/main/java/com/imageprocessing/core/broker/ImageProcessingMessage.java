package com.imageprocessing.core.broker;

public record ImageProcessingMessage(
        String imageId,
        String processedImageId
) {
}