package com.api.image_processing_event_driven.broker;

import com.api.image_processing_event_driven.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ImageConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE)
    public void receiveProcessingRequest(ImageProcessingMessage message) {
        System.out.println("MISSATGE REBUT DE RABBITMQ: " + message);
    }
}