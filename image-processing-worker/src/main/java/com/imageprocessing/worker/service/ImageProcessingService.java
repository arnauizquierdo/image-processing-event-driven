package com.imageprocessing.worker.service;

import com.imageprocessing.core.broker.ImageProcessingMessage;

public interface ImageProcessingService {
    void processImage(ImageProcessingMessage message);
}
