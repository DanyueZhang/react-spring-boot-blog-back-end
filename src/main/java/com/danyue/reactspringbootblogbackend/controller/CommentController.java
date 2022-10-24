package com.danyue.reactspringbootblogbackend.controller;

import com.danyue.reactspringbootblogbackend.entity.BlogComment;
import com.danyue.reactspringbootblogbackend.entity.BlogCommentVO;
import com.danyue.reactspringbootblogbackend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/submitComment")
    public ResponseEntity<BlogComment> submitComment(@RequestBody BlogCommentVO blogCommentVO) {
        return commentService.saveComment(blogCommentVO);
    }

    @GetMapping("/getAboutCommentsPage/{page}")
    public ResponseEntity<Page<BlogComment>> getAboutCommentsPage(@PathVariable("page") int page) {
        return ResponseEntity.ok().body(commentService.getAboutCommentsPage(page));
    }

    @GetMapping("/getCommentsPge/{page}")
    @PreAuthorize("hasAuthority('admin:getCommentsPage')")
    public ResponseEntity<Page<BlogComment>> getCommentsPge(@PathVariable("page") int page) {
        return ResponseEntity.ok().body(commentService.getCommentsPage(page));
    }

    @DeleteMapping("/deleteComment/{id}")
    @PreAuthorize("hasAuthority('admin:deleteComment')")
    public ResponseEntity<String> deleteComment(@PathVariable("id") Long id) {
        commentService.deleteComment(id);

        return ResponseEntity.ok("Delete a comment successfully!");
    }
}
