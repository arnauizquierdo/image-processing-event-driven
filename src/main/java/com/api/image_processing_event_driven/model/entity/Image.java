package com.api.image_processing_event_driven.model.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Document(collection = "images")
@CompoundIndex(name = "userId_originalFileName_unique", def = "{'userId': 1, 'originalFileName': 1}", unique = true)
public class Image {

    @Id
    private String id;

    private String userId;

    private String originalFileName;

    @Setter
    private String gridfsFileId;

    @Setter
    private Long fileSize;

    @Setter
    private String contentType;

    @CreatedDate
    private LocalDateTime createdAt;

    @Setter
    private List<ProcessedImage> processedImages = new ArrayList<>();;

    public Image() {}

    public Image(String userId, String originalFileName, String gridfsFileId, Long fileSize, String contentType) {
        this.userId = userId;
        this.originalFileName = originalFileName;
        this.gridfsFileId = gridfsFileId;
        this.fileSize = fileSize;
        this.contentType = contentType;
    }

    public void addProcessedImage(ProcessedImage processedImage) {
        processedImages.add(processedImage);
    }

    @Override
    public String toString() {
        return "Image{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", originalFileName='" + originalFileName + '\'' +
                ", gridfsFileId='" + gridfsFileId + '\'' +
                ", fileSize=" + fileSize +
                ", contentType='" + contentType + '\'' +
                ", createdAt=" + createdAt +
                ", processedImages=" + processedImages +
                '}';
    }
}