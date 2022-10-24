package com.danyue.reactspringbootblogbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogCommentVO {
    private String name;

    private String content;

    private String replyTo;

    private Long articleId;
}
