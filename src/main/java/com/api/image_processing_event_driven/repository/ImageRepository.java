package com.api.image_processing_event_driven.repository;

import com.api.image_processing_event_driven.model.entity.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ImageRepository extends MongoRepository<Image, String> {
    Optional<Image> findByOriginalFileName(String fileName);
}
