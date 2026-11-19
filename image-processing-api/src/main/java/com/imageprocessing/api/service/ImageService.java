package com.imageprocessing.api.service;

import com.imageprocessing.api.model.dto.ImageFileResponseDTO;
import com.imageprocessing.api.model.dto.ProcessImageResponseDTO;
import com.imageprocessing.api.model.dto.UploadResponseDTO;
import com.imageprocessing.core.entity.ProcessedImage;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    UploadResponseDTO uploadImage(String username, MultipartFile file) throws IOException;

    ImageFileResponseDTO downloadImage(String id, String username) throws IOException;

    ProcessImageResponseDTO processImage(String imageId, String waterMarkText, String username);

    ImageFileResponseDTO downloadProcessedImage(String imageId, String processedImageId, String username) throws IOException;

    ProcessedImage getProcessingStatus(String username, String imageId, String processedImageId);
}
