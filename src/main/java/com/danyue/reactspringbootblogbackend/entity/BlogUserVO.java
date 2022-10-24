package com.danyue.reactspringbootblogbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BlogUserVO {

    private String username;

    private String token;

    public BlogUserVO(BlogUser blogUser, String token) {
        username = blogUser.getUsername();
        this.token = token;
    }
}
