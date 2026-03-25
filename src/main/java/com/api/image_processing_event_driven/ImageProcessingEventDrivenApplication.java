package com.api.image_processing_event_driven;

import com.api.image_processing_event_driven.model.entity.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.api.image_processing_event_driven.repository.UserRepository;
import com.api.image_processing_event_driven.repository.ImageRepository;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class ImageProcessingEventDrivenApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImageProcessingEventDrivenApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(UserRepository userRepository, ImageRepository imageRepository) {

		return (args) -> {

			User newUser1 = new User("usuari1", "usuari1@gmail.com", "contrasenya123");
			newUser1.addUserRole(Role.ROLE_USER);
			userRepository.save(newUser1);

			User newUser2 = new User("usuari2", "usuari2@gmail.com", "contrasenya123");
			newUser2.addUserRole(Role.ROLE_USER);
			userRepository.save(newUser2);

			//String userId, String originalFileName, String gridfsFileId, Long fileSize, String contentType
			Image image1 = new Image(newUser1.getId(), "photo1.png", "1289361289036123", 30L, "image/png");

			ProcessedImage processedImage1 = new ProcessedImage("1_photo1.png");
			ProcessedImage processedImage2 = new ProcessedImage("2_photo2.png");
			image1.addProcessedImage(processedImage1);
			image1.addProcessedImage(processedImage2);

			imageRepository.save(image1);

			// -- Process the image

			processedImage1.markAsCompleted("7823648126347812");
			processedImage2.markAsProcessing();
			imageRepository.save(image1);

		};
	}

}
