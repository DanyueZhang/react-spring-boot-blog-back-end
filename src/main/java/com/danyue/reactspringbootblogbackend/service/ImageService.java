package com.danyue.reactspringbootblogbackend.service;

import com.danyue.reactspringbootblogbackend.entity.BlogImage;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    ResponseEntity<String> uploadImage(String name, MultipartFile file) throws IOException;

    Page<BlogImage> getImagesPage(int page);

    void deleteImage(long id);
}
