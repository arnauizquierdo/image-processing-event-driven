package com.api.image_processing_event_driven.model.entity;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class ProcessedImage {

    private String processedImageId;

    private String gridfsFileId;

    private String waterMarkText;

    private ProcessingStatus processingStatus;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public ProcessedImage() {}

    public ProcessedImage(String waterMarkText) {
        this.processedImageId = UUID.randomUUID().toString();
        this.waterMarkText = waterMarkText;
        this.processingStatus = ProcessingStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsProcessing() {
        this.processingStatus = ProcessingStatus.PROCESSING;
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsCompleted(String gridfsFileId) {
        this.processingStatus = ProcessingStatus.COMPLETED;
        this.gridfsFileId = gridfsFileId;
        this.updatedAt = LocalDateTime.now();
    }

    public void markAsFailed() {
        this.processingStatus = ProcessingStatus.FAILED;
        this.updatedAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "ProcessedImage{" +
                "processedImageId='" + processedImageId + '\'' +
                ", gridfsFileId='" + gridfsFileId + '\'' +
                ", waterMarkText='" + waterMarkText + '\'' +
                ", processingStatus=" + processingStatus +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }

    public void markAsPending() {
        this.processingStatus = ProcessingStatus.PENDING;
        this.updatedAt = LocalDateTime.now();
    }
}
