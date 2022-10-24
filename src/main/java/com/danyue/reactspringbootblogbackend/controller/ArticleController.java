package com.danyue.reactspringbootblogbackend.controller;

import com.danyue.reactspringbootblogbackend.entity.BlogArticle;
import com.danyue.reactspringbootblogbackend.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/article")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @PostMapping("/saveArticle")
    @PreAuthorize("hasAuthority('admin:saveArticle')")
    public ResponseEntity<String> saveArticle(@RequestBody BlogArticle blogArticle) {
        return articleService.saveArticle(blogArticle);
    }

    @GetMapping("/getArticlesPage/{page}")
    @PreAuthorize("hasAuthority('admin:getArticlesPage')")
    public ResponseEntity<Page<BlogArticle>> getArticlesPage(@PathVariable("page") int page) {
        return ResponseEntity.ok().body(articleService.getArticlesPage(page));
    }

    @GetMapping("/getArticle/{id}")
    @PreAuthorize("hasAuthority('admin:getArticle')")
    public ResponseEntity<BlogArticle> getArticle(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(articleService.getArticle(id));
    }

    @GetMapping("/getArticlePublic/{id}")
    public ResponseEntity<BlogArticle> getArticlePublic(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(articleService.getArticlePublic(id));
    }

    @DeleteMapping("/deleteArticle/{id}")
    @PreAuthorize("hasAuthority('admin:deleteArticle')")
    public ResponseEntity<String> deleteArticle(@PathVariable("id") Long id) {
        articleService.deleteArticle(id);

        return ResponseEntity.ok("Delete an article successfully!");
    }

    @GetMapping("/getArticlesPagePublic/{page}")
    public ResponseEntity<Page<BlogArticle>> getArticlesPagePublic(@PathVariable("page") int page) {
        return ResponseEntity.ok().body(articleService.getArticlesPagePublic(page));
    }

    @GetMapping("/getArticlesPageByTagId/{page}/{tagId}")
    public ResponseEntity<Page<BlogArticle>> getArticlesPageByTagId(@PathVariable("page") int page,
                                                                    @PathVariable("tagId") Long tagId) {
        return ResponseEntity.ok().body(articleService.getArticlesPageByTagId(page, tagId));
    }

    @GetMapping("/likeArticle/{id}")
    public ResponseEntity<String> likeArticle(@PathVariable("id") Long id) {
        articleService.likeArticle(id);

        return ResponseEntity.ok("Like an article successfully!");
    }
}
