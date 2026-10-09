package com.api.image_processing_event_driven.service;

import com.api.image_processing_event_driven.mapper.ImageMapper;
import com.api.image_processing_event_driven.mapper.ProcessImageMapper;
import com.api.image_processing_event_driven.model.dto.ImageFileResponseDTO;
import com.api.image_processing_event_driven.model.dto.ProcessImageResponseDTO;
import com.api.image_processing_event_driven.model.dto.UploadResponseDTO;
import com.api.image_processing_event_driven.model.entity.Image;
import com.api.image_processing_event_driven.model.entity.ProcessedImage;
import com.api.image_processing_event_driven.model.entity.ProcessingStatus;
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
import java.time.LocalDateTime;

@Service
public class ImageService {

    private final GridFsTemplate gridFsTemplate;
    private final ImageRepository imageRepository;
    private final ImageMapper imageMapper;
    private final ProcessImageMapper processImageMapper;

    public ImageService(GridFsTemplate gridFsTemplate, ImageRepository imageRepository, ImageMapper imageMapper, ProcessImageMapper processImageMapper) {
        this.gridFsTemplate = gridFsTemplate;
        this.imageRepository = imageRepository;
        this.imageMapper = imageMapper;
        this.processImageMapper = processImageMapper;
    }

    public UploadResponseDTO uploadImage(String username, MultipartFile file) throws IOException {
        var gridFsId = gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType());
        Image image = new Image(username, file.getOriginalFilename(), gridFsId.toString(), file.getSize(), file.getContentType());
        imageRepository.save(image);
        return imageMapper.imageToUploadResponseDTO(image);
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

    public ProcessImageResponseDTO processImage(String imageId, String waterMarkText, String username) {
        Image image = imageRepository.findById(imageId).orElseThrow(() -> new RuntimeException("Image not found"));
        if (!image.getUsername().equals(username)) {
            throw new RuntimeException("Not allowed to access this image");
        }
        // creem el job
        ProcessedImage processedImage = new ProcessedImage(waterMarkText);
        processedImage.markAsPending();
        image.addProcessedImage(processedImage);
        imageRepository.save(image);
        //
        //To-Do: publicar l'event al broker
        //
        return processImageMapper.processImageMapperToProcessImageResponseDTO(processedImage);
    }

}
