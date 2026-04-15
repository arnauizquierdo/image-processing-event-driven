package com.api.image_processing_event_driven.service;

import com.api.image_processing_event_driven.mapper.ImageMapper;
import com.api.image_processing_event_driven.model.dto.ImageFileResponseDTO;
import com.api.image_processing_event_driven.model.entity.Image;
import com.api.image_processing_event_driven.repository.ImageRepository;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {

    private final GridFsTemplate gridFsTemplate;
    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;

    public ImageService(GridFsTemplate gridFsTemplate, ImageRepository imageRepository, ImageMapper imageMapper) {
        this.gridFsTemplate = gridFsTemplate;
        this.imageRepository = imageRepository;
        this.imageMapper = imageMapper;
    }

    public Image uploadImage(String username, MultipartFile file) throws IOException {
        var gridFsId = gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType());
        Image image = new Image(username, file.getOriginalFilename(), gridFsId.toString(), file.getSize(), file.getContentType());
        return imageRepository.save(image);
    }

    public ImageFileResponseDTO downloadImage(String id, String username) throws IOException {
        Image image = imageRepository.findById(id).orElseThrow(() -> new RuntimeException("Image not found"));
        if (!image.getUsername().equals(username)) {
            throw new RuntimeException("Not allowed to access this image");
        }
        GridFSFile gridFsFile = gridFsTemplate.findOne(
            Query.query(Criteria.where("_id").is(image.getGridfsFileId()))
        );
        GridFsResource resource = gridFsTemplate.getResource(gridFsFile);
        return imageMapper.toImageFileResponse(resource.getInputStream().readAllBytes(), resource.getContentType());
    }

}
