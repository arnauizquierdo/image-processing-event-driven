package com.imageprocessing.api.broker;

import com.imageprocessing.core.broker.RabbitMQConstants;
import com.imageprocessing.core.broker.ImageProcessingMessage;
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
                RabbitMQConstants.EXCHANGE,
                RabbitMQConstants.ROUTING_KEY,
                message
        );
        System.out.println("MISSATGE ENVIAT A RABBITMQ");
    }
}