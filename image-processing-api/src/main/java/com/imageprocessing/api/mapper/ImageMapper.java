package com.imageprocessing.api.mapper;

import com.imageprocessing.api.model.dto.ImageFileResponseDTO;
import com.imageprocessing.api.model.dto.UploadResponseDTO;
import com.imageprocessing.core.entity.Image;
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
