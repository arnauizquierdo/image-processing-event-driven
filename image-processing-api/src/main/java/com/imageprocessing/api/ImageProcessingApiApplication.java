package com.imageprocessing.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = {"com.imageprocessing.api.repository", "com.imageprocessing.core.repository"})
public class ImageProcessingApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ImageProcessingApiApplication.class, args);
    }
}