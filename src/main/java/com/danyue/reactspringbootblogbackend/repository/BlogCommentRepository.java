package com.danyue.reactspringbootblogbackend.repository;

import com.danyue.reactspringbootblogbackend.entity.BlogComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogCommentRepository extends JpaRepository<BlogComment, Long> {
    Page<BlogComment> findAllByBlogArticleNull(Pageable pageable);
}
