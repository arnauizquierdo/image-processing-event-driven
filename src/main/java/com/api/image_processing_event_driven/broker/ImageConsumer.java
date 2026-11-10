package com.api.image_processing_event_driven.broker;

import com.api.image_processing_event_driven.config.RabbitMQConfig;
import com.api.image_processing_event_driven.service.ImageProcessingService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ImageConsumer {

    private final ImageProcessingService imageProcessingService;

    public ImageConsumer(ImageProcessingService imageProcessingService) {
        this.imageProcessingService = imageProcessingService;
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void receiveProcessingRequest(ImageProcessingMessage message) {
        imageProcessingService.processImage(message);
    }

}