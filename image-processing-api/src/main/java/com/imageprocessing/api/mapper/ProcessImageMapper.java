package com.imageprocessing.api.mapper;


import com.imageprocessing.api.model.dto.ProcessImageResponseDTO;
import com.imageprocessing.core.entity.ProcessedImage;
import org.springframework.stereotype.Component;

@Component
public class ProcessImageMapper {

    public ProcessImageResponseDTO processImageToProcessImageResponseDTO(ProcessedImage processedImage) {
        return new ProcessImageResponseDTO(
                processedImage.getProcessedImageId(),
                processedImage.getProcessingStatus(),
                processedImage.getCreatedAt()
        );
    }
}
