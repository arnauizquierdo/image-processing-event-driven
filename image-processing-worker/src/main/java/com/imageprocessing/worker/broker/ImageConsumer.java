package com.imageprocessing.worker.broker;

import com.imageprocessing.core.broker.ImageProcessingMessage;
import com.imageprocessing.core.broker.RabbitMQConstants;
import com.imageprocessing.worker.service.ImageProcessingService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ImageConsumer {

    private final ImageProcessingService imageProcessingService;

    public ImageConsumer(ImageProcessingService imageProcessingService) {
        this.imageProcessingService = imageProcessingService;
    }

    @RabbitListener(queues = RabbitMQConstants.QUEUE)
    public void receiveProcessingRequest(ImageProcessingMessage message) {
        imageProcessingService.processImage(message);
    }
}