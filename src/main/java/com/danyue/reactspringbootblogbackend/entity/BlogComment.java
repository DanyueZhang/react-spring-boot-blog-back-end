package com.danyue.reactspringbootblogbackend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "blog_comment")
public class BlogComment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String content;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "reply_to")
    private String replyTo;

    @Column(name = "article_name")
    private String articleName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    @JsonIgnore
    private BlogArticle blogArticle;

    public BlogComment(BlogCommentVO blogCommentVO) {
        this.name = blogCommentVO.getName();
        this.content = blogCommentVO.getContent();
        this.createTime = new Date();
    }
}
