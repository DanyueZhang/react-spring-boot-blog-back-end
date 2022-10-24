package com.danyue.reactspringbootblogbackend.repository;

import com.danyue.reactspringbootblogbackend.entity.BlogImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogImageRepository extends JpaRepository<BlogImage, Long> {
    BlogImage findByName(String name);
}
