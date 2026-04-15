package com.api.image_processing_event_driven.controller;

import com.api.image_processing_event_driven.model.dto.ImageFileResponseDTO;
import com.api.image_processing_event_driven.model.entity.Image;
import com.api.image_processing_event_driven.service.ImageService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Image> uploadImage(Authentication authentication, @RequestParam("file") MultipartFile file) throws Exception {
        Image image = imageService.uploadImage(authentication.getName(), file);
        return ResponseEntity.ok(image);
    }

    @GetMapping("/image/{id}/download")
    public ResponseEntity<byte[]> downloadImage(Authentication authentication, @PathVariable String id) throws IOException {
        ImageFileResponseDTO response = imageService.downloadImage(id, authentication.getName());
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(response.contentType()))
            .body(response.data());
    }


}
