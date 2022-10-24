package com.danyue.reactspringbootblogbackend.service;

import com.danyue.reactspringbootblogbackend.entity.BlogUser;
import com.danyue.reactspringbootblogbackend.entity.BlogUserVO;

public interface LoginService {
    BlogUserVO login(BlogUser blogUser);

    void logout();
}
