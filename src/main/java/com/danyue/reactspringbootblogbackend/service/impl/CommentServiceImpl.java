package com.danyue.reactspringbootblogbackend.service.impl;

import com.danyue.reactspringbootblogbackend.entity.BlogComment;
import com.danyue.reactspringbootblogbackend.entity.BlogCommentVO;
import com.danyue.reactspringbootblogbackend.repository.BlogArticleRepository;
import com.danyue.reactspringbootblogbackend.repository.BlogCommentRepository;
import com.danyue.reactspringbootblogbackend.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;

import static com.danyue.reactspringbootblogbackend.constants.SystemConstants.PAGE_SIZE;

@Service
public class CommentServiceImpl implements CommentService {

    private final BlogCommentRepository blogCommentRepository;

    private final BlogArticleRepository blogArticleRepository;

    @Autowired
    public CommentServiceImpl(BlogCommentRepository blogCommentRepository, BlogArticleRepository blogArticleRepository) {
        this.blogCommentRepository = blogCommentRepository;
        this.blogArticleRepository = blogArticleRepository;
    }

    @Override
    public ResponseEntity<BlogComment> saveComment(BlogCommentVO blogCommentVO) {
        if (!StringUtils.hasText(blogCommentVO.getName())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        if (!StringUtils.hasText(blogCommentVO.getContent())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        BlogComment blogComment = new BlogComment(blogCommentVO);

        if (!Objects.isNull(blogCommentVO.getArticleId())) {
            blogArticleRepository.findById(blogCommentVO.getArticleId()).ifPresent(article -> {
                blogComment.setBlogArticle(article);
                blogComment.setArticleName(article.getTitle());
            });
        }

        if (StringUtils.hasText(blogCommentVO.getReplyTo())) {
            blogComment.setReplyTo(blogCommentVO.getReplyTo());
        }

        blogCommentRepository.save(blogComment);

        return ResponseEntity.ok().body(blogComment);
    }

    @Override
    public Page<BlogComment> getAboutCommentsPage(int page) {
        return blogCommentRepository
                .findAllByBlogArticleNull(PageRequest.of(page, PAGE_SIZE, Sort.by("createTime").descending()));
    }

    @Override
    public Page<BlogComment> getCommentsPage(int page) {
        return blogCommentRepository.findAll(PageRequest.of(page, PAGE_SIZE, Sort.by("articleName")));
    }

    @Override
    public void deleteComment(Long id) {
        blogCommentRepository.deleteById(id);
    }
}
