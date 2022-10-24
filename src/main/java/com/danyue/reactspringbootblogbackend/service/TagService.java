package com.danyue.reactspringbootblogbackend.service;

import com.danyue.reactspringbootblogbackend.entity.BlogTag;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TagService {
    ResponseEntity<String> addNewTag(String tagName);

    Page<BlogTag> getTagsPage(int page);

    ResponseEntity<String> editTag(BlogTag newTag);

    void deleteTag(long tagId);

    List<BlogTag> getAllTags();

    List<BlogTag> getUsedTags();
}
