package com.api.image_processing_event_driven.controller;

import com.api.image_processing_event_driven.model.entity.Image;
import com.api.image_processing_event_driven.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Image> uploadImage(@RequestParam("userId") String userId, @RequestParam("file") MultipartFile file) throws Exception {
        Image image = imageService.uploadImage(userId, file);
        return ResponseEntity.ok(image);
    }
}
