package com.danyue.reactspringbootblogbackend.service.impl;

import com.danyue.reactspringbootblogbackend.entity.BlogImage;
import com.danyue.reactspringbootblogbackend.repository.BlogImageRepository;
import com.danyue.reactspringbootblogbackend.service.ImageService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

import static com.danyue.reactspringbootblogbackend.constants.SystemConstants.*;

@Service
public class ImageServiceImpl implements ImageService {

    private final BlogImageRepository blogImageRepository;

    @Autowired
    public ImageServiceImpl(BlogImageRepository blogImageRepository) {
        this.blogImageRepository = blogImageRepository;
    }

    @Override
    public ResponseEntity<String> uploadImage(String name, MultipartFile file) throws IOException {
        if (!StringUtils.hasText(name)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Image name is empty!");
        }

        if (!Objects.isNull(blogImageRepository.findByName(name))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Image name already exists!");
        }

        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Image file is empty!");
        }

        if (!Arrays.asList("image/jpeg", "image/png","image/gif").contains(file.getContentType())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is not an image!");
        }

        String filename = name + "-" + UUID.randomUUID() + "-" + file.getOriginalFilename();

        file.transferTo(new File(STORAGE_IMAGE_URL + filename));

        blogImageRepository.save(new BlogImage(name, filename));

        return ResponseEntity.ok().body("Upload an image successfully!");
    }

    @Override
    public Page<BlogImage> getImagesPage(int page) {
        Page<BlogImage> pages = blogImageRepository.findAll(PageRequest.of(page, PAGE_SIZE));

        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();

        pages.stream().forEach(p -> p.setFilepath(baseUrl + REQUEST_IMAGE_URL + p.getFilepath()));

        return pages;
    }

    @Override
    public void deleteImage(long id) {
        blogImageRepository.findById(id).ifPresent(image -> {
            try {
                FileUtils.forceDelete(new File(STORAGE_IMAGE_URL + image.getFilepath()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        blogImageRepository.deleteById(id);
    }
}
