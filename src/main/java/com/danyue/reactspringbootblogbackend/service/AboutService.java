package com.danyue.reactspringbootblogbackend.service;

import com.danyue.reactspringbootblogbackend.entity.AboutInfo;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AboutService {
    ResponseEntity<String> changeAbout(String content, MultipartFile file) throws IOException;

    AboutInfo getAbout();
}
