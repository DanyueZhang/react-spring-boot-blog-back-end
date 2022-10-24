package com.danyue.reactspringbootblogbackend.repository;

import com.danyue.reactspringbootblogbackend.entity.BlogTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BlogTagRepository extends JpaRepository<BlogTag, Long> {
    BlogTag findByName(String name);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM blog_article_tag WHERE id_tag = ?1", nativeQuery = true)
    void deleteArticleTagById(Long id);

    @Query(value = """
            select *
            from
                blog_tag bt
            where
                bt.id in (select bat.id_tag from blog_article_tag bat)
            """, nativeQuery = true)
    List<BlogTag> findUsedTags();
}
