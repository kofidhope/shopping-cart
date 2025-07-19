package com.dhopecode.shoppingCart.service.images;

import com.dhopecode.shoppingCart.dto.ImageDto;
import com.dhopecode.shoppingCart.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDto>  saveImages(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file, Long imageId);
}
