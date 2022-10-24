package com.danyue.reactspringbootblogbackend.service.impl;

import com.danyue.reactspringbootblogbackend.entity.BlogTag;
import com.danyue.reactspringbootblogbackend.repository.BlogTagRepository;
import com.danyue.reactspringbootblogbackend.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

import static com.danyue.reactspringbootblogbackend.constants.SystemConstants.PAGE_SIZE;

@Service
public class TagServiceImpl implements TagService {

    private final BlogTagRepository blogTagRepository;

    @Autowired
    public TagServiceImpl(BlogTagRepository blogTagRepository) {
        this.blogTagRepository = blogTagRepository;
    }

    @Override
    public ResponseEntity<String> addNewTag(String tagName) {
        if (!StringUtils.hasText(tagName)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tag name is empty!");
        }

        if (!Objects.isNull(blogTagRepository.findByName(tagName))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tag already exists!");
        }

        blogTagRepository.save(new BlogTag(tagName));

        return ResponseEntity.ok("Add a new tag successfully!");
    }

    @Override
    public Page<BlogTag> getTagsPage(int page) {
        return blogTagRepository.findAll(PageRequest.of(page, PAGE_SIZE));
    }

    @Override
    public ResponseEntity<String> editTag(BlogTag newTag) {
        String tagName = newTag.getName();

        if (!StringUtils.hasText(tagName)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tag name is empty!");
        }

        if (!Objects.isNull(blogTagRepository.findByName(tagName))) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tag already exists!");
        }

        blogTagRepository.save(newTag);

        return ResponseEntity.ok("Edit a tag successfully!");
    }

    @Override
    public void deleteTag(long tagId) {
        blogTagRepository.deleteArticleTagById(tagId);
        blogTagRepository.deleteById(tagId);
    }

    @Override
    public List<BlogTag> getAllTags() {
        return blogTagRepository.findAll();
    }

    @Override
    public List<BlogTag> getUsedTags() {
        return blogTagRepository.findUsedTags();
    }
}
