package com.imageprocessing.core.repository;

import com.imageprocessing.core.entity.Image;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ImageRepository extends MongoRepository<Image, String> {
    Optional<Image> findByOriginalFileName(String fileName);
}
