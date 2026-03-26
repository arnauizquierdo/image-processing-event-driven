package com.api.image_processing_event_driven.controller;

import com.api.image_processing_event_driven.model.dto.ImageFileResponseDTO;
import com.api.image_processing_event_driven.model.entity.Image;
import com.api.image_processing_event_driven.service.ImageService;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Image> uploadImage(@RequestParam("userId") String userId, @RequestParam("file") MultipartFile file) throws Exception {
        Image image = imageService.uploadImage(userId, file);
        return ResponseEntity.ok(image);
    }

    @GetMapping("/file/{gridfsId}")
    public ResponseEntity<byte[]> getImage(@PathVariable String gridfsId) throws IOException {
        ImageFileResponseDTO response = imageService.getImageFile(gridfsId);
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(response.contentType()))
            .body(response.data());
    }


}
