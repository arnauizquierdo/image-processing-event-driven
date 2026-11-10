package com.api.image_processing_event_driven.service;

import com.api.image_processing_event_driven.broker.ImageProcessingMessage;

public interface ImageProcessingService {
    void processImage(ImageProcessingMessage message);
}
