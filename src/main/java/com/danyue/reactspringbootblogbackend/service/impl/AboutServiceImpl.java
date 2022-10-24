package com.danyue.reactspringbootblogbackend.service.impl;

import com.alibaba.fastjson.JSON;
import com.danyue.reactspringbootblogbackend.entity.AboutInfo;
import com.danyue.reactspringbootblogbackend.service.AboutService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

import static com.danyue.reactspringbootblogbackend.constants.SystemConstants.REQUEST_IMAGE_URL;
import static com.danyue.reactspringbootblogbackend.constants.SystemConstants.STORAGE_IMAGE_URL;

@Service
public class AboutServiceImpl implements AboutService {

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public AboutServiceImpl(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate =redisTemplate;
    }

    @Override
    public ResponseEntity<String> changeAbout(String content, MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Image file is empty!");
        }

        if (!Arrays.asList("image/jpeg", "image/png","image/gif").contains(file.getContentType())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("File is not an image!");
        }

        String filename = UUID.randomUUID() + "-" + file.getOriginalFilename();

        AboutInfo newAboutInfo = new AboutInfo(filename, content);

        String oldAboutInfoStr = redisTemplate.opsForValue().get("aboutInfo");

        // delete old image
        if (!Objects.isNull(oldAboutInfoStr)) {
            AboutInfo oldAboutInfo = JSON.parseObject(oldAboutInfoStr, AboutInfo.class);

            FileUtils.forceDelete(new File(STORAGE_IMAGE_URL + oldAboutInfo.getAvatar()));
        }

        // save new image to local storage
        file.transferTo(new File(STORAGE_IMAGE_URL + filename));

        // save new about information to redis
        redisTemplate.opsForValue().set("aboutInfo", JSON.toJSONString(newAboutInfo));

        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();

        newAboutInfo.setAvatar(baseUrl + REQUEST_IMAGE_URL + filename);

        return ResponseEntity.ok().body("Change about information successfully!");
    }

    @Override
    public AboutInfo getAbout() {
        String aboutInfoStr = redisTemplate.opsForValue().get("aboutInfo");

        if (Objects.isNull(aboutInfoStr)) {
            return null;
        }

        AboutInfo aboutInfo = JSON.parseObject(aboutInfoStr, AboutInfo.class);

        String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().toUriString();

        aboutInfo.setAvatar(baseUrl + REQUEST_IMAGE_URL + aboutInfo.getAvatar());

        return aboutInfo;
    }
}
