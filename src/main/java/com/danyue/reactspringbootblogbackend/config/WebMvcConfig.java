package com.danyue.reactspringbootblogbackend.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.danyue.reactspringbootblogbackend.constants.SystemConstants.REQUEST_IMAGE_URL;
import static com.danyue.reactspringbootblogbackend.constants.SystemConstants.STORAGE_IMAGE_URL;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(REQUEST_IMAGE_URL + "**").addResourceLocations("file:"+ STORAGE_IMAGE_URL);
    }
}
