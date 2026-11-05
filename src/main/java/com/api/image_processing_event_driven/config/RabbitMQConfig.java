
package com.api.image_processing_event_driven.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE = "image.processing.exchange";
    public static final String QUEUE = "image.processing.queue";
    public static final String ROUTING_KEY = "image.processing";

    @Bean
    public DirectExchange imageProcessingExchange() {
        return new DirectExchange(EXCHANGE);
    }

    @Bean
    public Queue imageProcessingQueue() {
        return new Queue(QUEUE, true);
    }

    @Bean
    public Binding imageProcessingBinding(Queue imageProcessingQueue, DirectExchange imageProcessingExchange) {
        return BindingBuilder
                .bind(imageProcessingQueue)
                .to(imageProcessingExchange)
                .with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
