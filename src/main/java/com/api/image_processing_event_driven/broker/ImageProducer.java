package com.api.image_processing_event_driven.broker;

import com.api.image_processing_event_driven.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ImageProducer {

    private final RabbitTemplate rabbitTemplate;

    public ImageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendProcessingRequest(ImageProcessingMessage message) {
        System.out.println("PUBLICANT MISSATGE A RABBITMQ: " + message);
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                message
        );
        System.out.println("MISSATGE ENVIAT A RABBITMQ");
    }
}