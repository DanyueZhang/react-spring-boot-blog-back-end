package com.danyue.reactspringbootblogbackend.controller;

import com.danyue.reactspringbootblogbackend.entity.BlogImage;
import com.danyue.reactspringbootblogbackend.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/image")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/uploadImage")
    @PreAuthorize("hasAuthority('admin:uploadImage')")
    public ResponseEntity<String> uploadImage(@RequestParam(value = "file") MultipartFile file,
                                              @RequestParam("name") String name) throws IOException {
        return imageService.uploadImage(name, file);
    }

    @GetMapping("/getImagesPage/{page}")
    @PreAuthorize("hasAuthority('admin:getImagesPage')")
    public ResponseEntity<Page<BlogImage>> getImagesPage(@PathVariable("page") int page) {
        return ResponseEntity.ok().body(imageService.getImagesPage(page));
    }

    @DeleteMapping("/deleteImage/{id}")
    @PreAuthorize("hasAuthority('admin:deleteImage')")
    public ResponseEntity<String> deleteImage(@PathVariable("id") Long id) {
        imageService.deleteImage(id);
        
        return ResponseEntity.ok("Delete a image successfully!");
    }
}
