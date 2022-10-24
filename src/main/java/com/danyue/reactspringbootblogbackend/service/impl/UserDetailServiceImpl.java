package com.danyue.reactspringbootblogbackend.service.impl;

import com.danyue.reactspringbootblogbackend.entity.BlogUser;
import com.danyue.reactspringbootblogbackend.entity.LoginUser;
import com.danyue.reactspringbootblogbackend.repository.BlogUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

    private final BlogUserRepository blogUserRepository;

    @Autowired
    public UserDetailServiceImpl(BlogUserRepository blogUserRepository) {
        this.blogUserRepository = blogUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BlogUser blogUser = blogUserRepository.findByUsername(username);

        if (Objects.isNull(blogUser)) {
            throw new RuntimeException("The username or password is incorrect!");
        }

        List<String> permissions = blogUserRepository.findPermissionByUserId(blogUser.getId());

        return new LoginUser(blogUser, permissions);
    }
}
