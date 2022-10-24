package com.danyue.reactspringbootblogbackend.service;

import com.danyue.reactspringbootblogbackend.entity.BlogComment;
import com.danyue.reactspringbootblogbackend.entity.BlogCommentVO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface CommentService {
    ResponseEntity<BlogComment> saveComment(BlogCommentVO blogCommentVO);

    Page<BlogComment> getAboutCommentsPage(int page);

    Page<BlogComment> getCommentsPage(int page);

    void deleteComment(Long id);
}
