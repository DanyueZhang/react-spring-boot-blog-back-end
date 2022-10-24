package com.danyue.reactspringbootblogbackend.service.impl;

import com.danyue.reactspringbootblogbackend.entity.BlogArticle;
import com.danyue.reactspringbootblogbackend.repository.BlogArticleRepository;
import com.danyue.reactspringbootblogbackend.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.Objects;

import static com.danyue.reactspringbootblogbackend.constants.SystemConstants.PAGE_SIZE;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final BlogArticleRepository blogArticleRepository;

    @Autowired
    public ArticleServiceImpl(BlogArticleRepository blogArticleRepository) {
        this.blogArticleRepository = blogArticleRepository;
    }

    @Override
    public ResponseEntity<String> saveArticle(BlogArticle blogArticle) {
        if (!StringUtils.hasText(blogArticle.getTitle())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Title is empty!");
        }

        if (!StringUtils.hasText(blogArticle.getIntroduction())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Introduction is empty!");
        }

        if (!StringUtils.hasText(blogArticle.getContent())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Content is empty!");
        }

        if (Objects.isNull(blogArticle.getCreateTime())) {
            blogArticle.setCreateTime(new Date());
        }

        blogArticleRepository.save(blogArticle);

        return ResponseEntity.ok("Save a article successfully!");
    }

    @Override
    public Page<BlogArticle> getArticlesPage(int page) {
        return blogArticleRepository
                .findAll(PageRequest.of(page, PAGE_SIZE, Sort.by("createTime").descending()));
    }

    @Override
    public BlogArticle getArticle(Long id) {
        return blogArticleRepository.findById(id).orElse(null);
    }

    @Override
    public BlogArticle getArticlePublic(Long id) {
        return blogArticleRepository.findByIdAndStatus(id, true);
    }

    @Override
    public void deleteArticle(Long id) {
        blogArticleRepository.deleteById(id);
    }

    @Override
    public Page<BlogArticle> getArticlesPagePublic(int page) {
        return blogArticleRepository.
                findAllByStatus(true,
                        PageRequest.of(page, PAGE_SIZE, Sort.by("createTime").descending()));
    }

    @Override
    public Page<BlogArticle> getArticlesPageByTagId(int page, Long tagId) {
        return blogArticleRepository.
                findAllByStatusAndTags_Id(true, tagId,
                        PageRequest.of(page, PAGE_SIZE, Sort.by("createTime").descending()));
    }

    @Override
    public void likeArticle(Long id) {
        blogArticleRepository.findById(id).ifPresent(article -> {
            article.setLikeCount(article.getLikeCount() + 1);
            blogArticleRepository.save(article);
        });
    }
}
