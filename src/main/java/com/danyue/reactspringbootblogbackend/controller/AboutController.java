package com.danyue.reactspringbootblogbackend.controller;

import com.danyue.reactspringbootblogbackend.entity.AboutInfo;
import com.danyue.reactspringbootblogbackend.service.AboutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/about")
public class AboutController {

    private final AboutService aboutService;

    @Autowired
    public AboutController(AboutService aboutService) {
        this.aboutService = aboutService;
    }

    @PutMapping("/changeAbout")
    @PreAuthorize("hasAuthority('admin:changeAbout')")
    public ResponseEntity<String> changeAbout(@RequestParam(value = "file") MultipartFile file,
                                                 @RequestParam("content") String content) throws IOException {
        return aboutService.changeAbout(content, file);
    }

    @GetMapping("/getAbout")
    public ResponseEntity<AboutInfo> getAbout() {
        return ResponseEntity.ok().body(aboutService.getAbout());
    }
}
