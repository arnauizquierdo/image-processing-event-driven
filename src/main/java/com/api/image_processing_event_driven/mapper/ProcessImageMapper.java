package com.api.image_processing_event_driven.mapper;


import com.api.image_processing_event_driven.model.dto.ProcessImageResponseDTO;
import com.api.image_processing_event_driven.model.entity.Image;
import com.api.image_processing_event_driven.model.entity.ProcessedImage;
import org.springframework.stereotype.Component;

@Component
public class ProcessImageMapper {

    public ProcessImageResponseDTO processImageMapperToProcessImageResponseDTO(ProcessedImage processedImage) {
        return new ProcessImageResponseDTO(
                processedImage.getProcessedImageId(),
                processedImage.getProcessingStatus(),
                processedImage.getCreatedAt()
        );
    }
}
