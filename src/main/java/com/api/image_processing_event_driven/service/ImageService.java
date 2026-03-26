package com.api.image_processing_event_driven.service;

import com.api.image_processing_event_driven.model.entity.Image;
import com.api.image_processing_event_driven.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {

    private final GridFsTemplate gridFsTemplate;
    private final ImageRepository imageRepository;

    public ImageService(GridFsTemplate gridFsTemplate, ImageRepository imageRepository) {
        this.gridFsTemplate = gridFsTemplate;
        this.imageRepository = imageRepository;
    }

    public Image uploadImage(String userId, MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        var gridFsId = gridFsTemplate.store(file.getInputStream(), originalFileName, file.getContentType());
        Image image = new Image(userId, originalFileName, gridFsId.toString(), file.getSize(), file.getContentType());
        return imageRepository.save(image);
    }

}
