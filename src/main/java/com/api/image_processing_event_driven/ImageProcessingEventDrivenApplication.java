package com.api.image_processing_event_driven;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableRabbit
public class ImageProcessingEventDrivenApplication {

	public static void main(String[] args) {
		SpringApplication.run(ImageProcessingEventDrivenApplication.class, args);
	}

}
