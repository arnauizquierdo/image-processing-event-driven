package com.api.image_processing_event_driven;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.api.image_processing_event_driven.model.entity.User;
import com.api.image_processing_event_driven.repository.UserRepository;
import org.springframework.context.annotation.Bean;

import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class ImageProcessingEventDrivenApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImageProcessingEventDrivenApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(MongoTemplate mongoTemplate, UserRepository userRepository) {

		return (args) -> {
			/**/

		};
	}

}
