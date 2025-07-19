package com.dhopecode.shoppingCart.service.images;

import com.dhopecode.shoppingCart.dto.ImageDto;
import com.dhopecode.shoppingCart.exceptions.ResourceNotFoundException;
import com.dhopecode.shoppingCart.model.Image;
import com.dhopecode.shoppingCart.model.Product;
import com.dhopecode.shoppingCart.repository.ImageRepository;
import com.dhopecode.shoppingCart.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;
    private final IProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No image found with id: " + id));
    }

    @Override
    public void deleteImageById(Long id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No image found with id: " + id));
        imageRepository.delete(image);
    }

    @Override
    public List<ImageDto> saveImages(List<MultipartFile> files, Long productId) {
        Product product = productService.getProductById(productId);

        List<ImageDto> savedImageDtos = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename() != null ? file.getOriginalFilename() : "unknown");
                image.setFileType(file.getContentType());
                image.setImage(new SerialBlob(file.getBytes()));
                image.setProduct(product);

                // Save first time to generate ID
                Image savedImage = imageRepository.save(image);

                // Now set the download URL
                String downloadUrl = "/api/v1/images/image/download/" + savedImage.getId();
                savedImage.setDownloadUrl(downloadUrl);

                // Save again to persist the download URL
                savedImage = imageRepository.save(savedImage);

                // Prepare DTO
                ImageDto imageDto = new ImageDto();
                imageDto.setId(savedImage.getId());
                imageDto.setFileName(savedImage.getFileName());
                imageDto.setDownloadUrl(savedImage.getDownloadUrl());
                savedImageDtos.add(imageDto);

            } catch (IOException | SQLException e) {
                throw new RuntimeException("Failed to save image: " + e.getMessage(), e);
            }
        }

        return savedImageDtos;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        try {
            Image image = getImageById(imageId);
            image.setFileName(file.getOriginalFilename() != null ? file.getOriginalFilename() : "unknown");
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        } catch (IOException | SQLException e) {
            throw new RuntimeException("Failed to update image: " + e.getMessage(), e);
        }
    }
}
