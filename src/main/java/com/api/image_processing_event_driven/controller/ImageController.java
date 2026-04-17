package com.api.image_processing_event_driven.controller;

import com.api.image_processing_event_driven.model.dto.ImageFileResponseDTO;
import com.api.image_processing_event_driven.model.dto.ProcessImageRequestDTO;
import com.api.image_processing_event_driven.model.dto.ProcessImageResponseDTO;
import com.api.image_processing_event_driven.model.dto.UploadResponseDTO;
import com.api.image_processing_event_driven.model.entity.Image;
import com.api.image_processing_event_driven.service.ImageService;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<UploadResponseDTO> uploadImage(Authentication authentication, @RequestParam("file") MultipartFile file) throws Exception {
        UploadResponseDTO response = imageService.uploadImage(authentication.getName(), file);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/image/{id}/download")
    public ResponseEntity<byte[]> downloadImage(Authentication authentication, @PathVariable String id) throws IOException {
        ImageFileResponseDTO response = imageService.downloadImage(id, authentication.getName());
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(response.contentType()))
            .body(response.data());
    }


    @PostMapping("/process")
    public ResponseEntity<ProcessImageResponseDTO> processImage(@RequestBody ProcessImageRequestDTO request, Authentication authentication) {
        ProcessImageResponseDTO response = imageService.processImage(request.imageId(), request.watermarkText(), authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


}
