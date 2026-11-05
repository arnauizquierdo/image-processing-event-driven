package com.api.image_processing_event_driven;

import com.api.image_processing_event_driven.model.entity.*;
import com.api.image_processing_event_driven.service.JwtService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.api.image_processing_event_driven.repository.UserRepository;
import com.api.image_processing_event_driven.repository.ImageRepository;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
@EnableRabbit
public class ImageProcessingEventDrivenApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImageProcessingEventDrivenApplication.class, args);
	}

}
