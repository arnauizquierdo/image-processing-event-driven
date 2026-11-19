package com.imageprocessing.worker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.imageprocessing.core.repository")
@EnableRabbit
public class ImageProcessingWorkerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImageProcessingWorkerApplication.class, args);
    }
}