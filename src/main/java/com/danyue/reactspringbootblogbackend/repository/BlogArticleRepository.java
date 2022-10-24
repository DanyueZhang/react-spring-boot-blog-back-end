package com.danyue.reactspringbootblogbackend.repository;

import com.danyue.reactspringbootblogbackend.entity.BlogArticle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogArticleRepository extends JpaRepository<BlogArticle, Long> {
    Page<BlogArticle> findAllByStatus(boolean status, Pageable pageable);

    Page<BlogArticle> findAllByStatusAndTags_Id(boolean status, Long tagId, Pageable pageable);

    BlogArticle findByIdAndStatus(Long id, boolean status);
}
