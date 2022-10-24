package com.danyue.reactspringbootblogbackend.controller;

import com.danyue.reactspringbootblogbackend.entity.BlogTag;
import com.danyue.reactspringbootblogbackend.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {

    private final TagService tagService;

    @Autowired
    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping("/newTag")
    @PreAuthorize("hasAuthority('admin:addNewTag')")
    public ResponseEntity<String> addNewTag(@RequestParam("tagName") String tagName) {
        return tagService.addNewTag(tagName);
    }

    @GetMapping("/getAllTags")
    @PreAuthorize("hasAuthority('admin:getAllTags')")
    public ResponseEntity<List<BlogTag>> getAllTags() {
        return ResponseEntity.ok().body(tagService.getAllTags());
    }

    @GetMapping("/getTagsPage/{page}")
    @PreAuthorize("hasAuthority('admin:getTagsPage')")
    public ResponseEntity<Page<BlogTag>> getTagsPage(@PathVariable("page") int page) {
        return ResponseEntity.ok().body(tagService.getTagsPage(page));
    }

    @PutMapping("/editTag")
    @PreAuthorize("hasAuthority('admin:editTag')")
    public ResponseEntity<String> editTag(@RequestBody BlogTag newTag) {
        return tagService.editTag(newTag);
    }

    @DeleteMapping("/deleteTag/{tagId}")
    @PreAuthorize("hasAuthority('admin:deleteTag')")
    public ResponseEntity<String> deleteTag(@PathVariable("tagId") long tagId) {
        tagService.deleteTag(tagId);

        return ResponseEntity.ok("Delete a tag successfully!");
    }

    @GetMapping("/getUsedTags")
    public ResponseEntity<List<BlogTag>> getUsedTags() {
        return ResponseEntity.ok().body(tagService.getUsedTags());
    }
}
