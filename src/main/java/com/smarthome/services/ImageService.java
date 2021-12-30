package com.smarthome.services;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by gch
 */
public interface ImageService {

    void saveImageFile(Long productId, MultipartFile file);
}
