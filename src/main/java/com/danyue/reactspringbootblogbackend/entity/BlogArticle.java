package com.danyue.reactspringbootblogbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "blog_article")
public class BlogArticle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String introduction;

    private String content;

    private boolean status;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "like_count")
    private long likeCount;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "blog_article_tag",
            joinColumns = {@JoinColumn(name = "id_blog", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "id_tag", referencedColumnName = "id")})
    private List<BlogTag> tags = new ArrayList<>();

    @OneToMany(mappedBy = "blogArticle", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<BlogComment> comments;
}
