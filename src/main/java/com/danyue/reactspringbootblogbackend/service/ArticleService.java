package com.danyue.reactspringbootblogbackend.service;

import com.danyue.reactspringbootblogbackend.entity.BlogArticle;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface ArticleService {
    ResponseEntity<String> saveArticle(BlogArticle blogArticle);

    Page<BlogArticle> getArticlesPage(int page);

    BlogArticle getArticle(Long id);

    BlogArticle getArticlePublic(Long id);

    void deleteArticle(Long id);

    Page<BlogArticle> getArticlesPagePublic(int page);

    Page<BlogArticle> getArticlesPageByTagId(int page, Long tagId);

    void likeArticle(Long id);
}
