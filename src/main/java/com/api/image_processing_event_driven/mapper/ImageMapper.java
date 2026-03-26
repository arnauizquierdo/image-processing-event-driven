package com.api.image_processing_event_driven.mapper;

import com.api.image_processing_event_driven.model.dto.ImageFileResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class ImageMapper {

    public ImageFileResponseDTO toImageFileResponse (byte[] data, String contentType) {
        return new ImageFileResponseDTO(data, contentType);
    }

}
