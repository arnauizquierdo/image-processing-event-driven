package com.api.image_processing_event_driven.service;

import com.api.image_processing_event_driven.model.dto.ImageFileResponseDTO;
import com.api.image_processing_event_driven.model.dto.ProcessImageResponseDTO;
import com.api.image_processing_event_driven.model.dto.UploadResponseDTO;
import com.api.image_processing_event_driven.model.entity.ProcessedImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    UploadResponseDTO uploadImage(String username, MultipartFile file) throws IOException;

    ImageFileResponseDTO downloadImage(String id, String username) throws IOException;

    ProcessImageResponseDTO processImage(String imageId, String waterMarkText, String username);

    ImageFileResponseDTO downloadProcessedImage(String imageId, String processedImageId, String username) throws IOException;

    ProcessedImage getProcessingStatus(String username, String imageId, String processedImageId);
}
