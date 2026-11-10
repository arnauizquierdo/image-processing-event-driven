package com.api.image_processing_event_driven.service;

import com.api.image_processing_event_driven.broker.ImageProcessingMessage;
import com.api.image_processing_event_driven.model.entity.Image;
import com.api.image_processing_event_driven.model.entity.ProcessedImage;
import com.api.image_processing_event_driven.repository.ImageRepository;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Locale;

@Service
public class ImageProcessingServiceImpl implements ImageProcessingService{

    private final GridFsTemplate gridFsTemplate;
    private final ImageRepository imageRepository;

    public ImageProcessingServiceImpl(GridFsTemplate gridFsTemplate, ImageRepository imageRepository) {
        this.gridFsTemplate = gridFsTemplate;
        this.imageRepository = imageRepository;
    }

    public void processImage(ImageProcessingMessage message) {
        // original image object
        Image image = imageRepository.findById(message.imageId()).orElseThrow(() -> new RuntimeException("Image not found"));

        ProcessedImage processedImage = image.getProcessedImages().stream()
                .filter(p -> p.getProcessedImageId().equals(message.processedImageId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Processing request not found"));

        // update processedImage state before starting the process
        processedImage.markAsProcessing();
        imageRepository.save(image);

        try {
            // get original GridFS image from first "/upload"
            GridFSFile originalFile = gridFsTemplate.findOne(
                    Query.query(
                            Criteria.where("_id").is(image.getGridfsFileId())
                    )
            );
            if (originalFile == null) {
                throw new RuntimeException("Original image not found in GridFS");
            }
            // get original GridFS image file bytes
            GridFsResource resource = gridFsTemplate.getResource(originalFile);
            byte[] originalBytes;
            try (InputStream inputStream = resource.getInputStream()) {
                originalBytes = inputStream.readAllBytes();
            }

            // adding the watermark and saving into GridFS
            byte[] processedBytes = addWatermark(
                    originalBytes,
                    image.getContentType(),
                    processedImage.getWaterMarkText()
            );
            String filename = "processed-" +
                    processedImage.getProcessedImageId() +
                    getExtension(image.getContentType());
            var processedGridFsId = gridFsTemplate.store(
                    new ByteArrayInputStream(processedBytes),
                    filename,
                    image.getContentType()
            );

            // mark as completed and setting the new GridFS reference with processed file
            processedImage.markAsCompleted(processedGridFsId.toString());
            imageRepository.save(image);
            System.out.println("IMATGE PROCESSADA CORRECTAMENT: " + processedImage.getProcessedImageId());

        } catch (Exception e) {
            processedImage.markAsFailed();
            imageRepository.save(image);

            System.err.println("ERROR PROCESSANT LA IMATGE: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private byte[] addWatermark(byte[] originalBytes, String contentType, String watermarkText) throws Exception {

        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(originalBytes));

        if (originalImage == null) {
            throw new RuntimeException("Unsupported or invalid image");
        }

        String format = getFormat(contentType);

        //  making a copy to write on it
        BufferedImage result = new BufferedImage(
                originalImage.getWidth(),
                originalImage.getHeight(),
                format.equals("png") ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB
        );

        Graphics2D graphics = result.createGraphics();

        try {
            graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            graphics.drawImage(originalImage, 0, 0, null);
            int fontSize = Math.max(
                    18,
                    Math.min(result.getWidth(), result.getHeight()) / 20
            );
            graphics.setFont(new Font(
                    Font.SANS_SERIF,
                    Font.BOLD,
                    fontSize
            ));

            int margin = Math.max(15, fontSize);
            int textWidth = graphics.getFontMetrics().stringWidth(watermarkText);

            int x = Math.max(
                    margin,
                    result.getWidth() - textWidth - margin
            );

            int y = result.getHeight() - margin;

            // blurred black background
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f));
            graphics.setColor(Color.BLACK);
            graphics.drawString(watermarkText, x + 2, y + 2);

            // white text
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.85f));
            graphics.setColor(Color.WHITE);
            graphics.drawString(watermarkText, x, y);

        } finally {
            graphics.dispose();
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        boolean written = ImageIO.write(result, format, outputStream);
        if (!written) {
            throw new RuntimeException("Could not write image format: " + format);
        }
        return outputStream.toByteArray();
    }

    private String getFormat(String contentType) {
        if (contentType == null) {
            throw new RuntimeException("Missing image content type");
        }

        return switch (contentType.toLowerCase(Locale.ROOT)) {
            case "image/jpeg", "image/jpg" -> "jpg";
            case "image/png" -> "png";
            default -> throw new RuntimeException("Unsupported image type: " + contentType);
        };
    }

    private String getExtension(String contentType) {
        return "." + getFormat(contentType);
    }
}