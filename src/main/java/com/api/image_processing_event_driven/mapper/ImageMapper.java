package com.api.image_processing_event_driven.mapper;

import com.api.image_processing_event_driven.model.dto.ImageFileResponseDTO;
import com.api.image_processing_event_driven.model.dto.UploadResponseDTO;
import com.api.image_processing_event_driven.model.entity.Image;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {

    public ImageFileResponseDTO toImageFileResponse (byte[] data, String contentType) {
        return new ImageFileResponseDTO(data, contentType);
    }

    public UploadResponseDTO imageToUploadResponseDTO (Image image) {
        return new UploadResponseDTO(
            image.getId(),
            image.getUsername(),
            image.getOriginalFileName(),
            image.getFileSize(),
            image.getContentType(),
            image.getCreatedAt()
        );
    }

}
