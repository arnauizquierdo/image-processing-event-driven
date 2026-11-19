package com.imageprocessing.core.broker;

public final class RabbitMQConstants {

    public static final String EXCHANGE = "image.processing.exchange";
    public static final String QUEUE = "image.processing.queue";
    public static final String ROUTING_KEY = "image.processing";

    private RabbitMQConstants() { }
}