package com.imageprocessing.worker.config;

import com.imageprocessing.core.broker.RabbitMQConstants;
import com.imageprocessing.core.config.RabbitMessageConverterConfig;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(RabbitMessageConverterConfig.class)
public class RabbitMQConfig {

    @Bean
    public DirectExchange imageProcessingExchange() {
        return new DirectExchange(RabbitMQConstants.EXCHANGE);
    }

    @Bean
    public Queue imageProcessingQueue() {
        return new Queue(RabbitMQConstants.QUEUE, true);
    }

    @Bean
    public Binding imageProcessingBinding(Queue imageProcessingQueue, DirectExchange imageProcessingExchange) {
        return BindingBuilder
                .bind(imageProcessingQueue)
                .to(imageProcessingExchange)
                .with(RabbitMQConstants.ROUTING_KEY);
    }
}