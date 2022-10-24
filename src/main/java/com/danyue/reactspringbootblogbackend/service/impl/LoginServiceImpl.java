package com.danyue.reactspringbootblogbackend.service.impl;

import com.alibaba.fastjson.JSON;
import com.danyue.reactspringbootblogbackend.entity.BlogUser;
import com.danyue.reactspringbootblogbackend.entity.BlogUserVO;
import com.danyue.reactspringbootblogbackend.entity.LoginUser;
import com.danyue.reactspringbootblogbackend.service.LoginService;
import com.danyue.reactspringbootblogbackend.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static com.danyue.reactspringbootblogbackend.constants.SystemConstants.REDIS_EXPIRE_TIME;

@Service
public class LoginServiceImpl implements LoginService {

    private final AuthenticationManager authenticationManager;

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public LoginServiceImpl(AuthenticationManager authenticationManager, RedisTemplate<String, String> redisTemplate) {
        this.authenticationManager = authenticationManager;
        this.redisTemplate = redisTemplate;
    }

    @Override
    public BlogUserVO login(BlogUser blogUser) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(blogUser.getUsername(), blogUser.getPassword());

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        if (Objects.isNull(authentication)) {
            throw new RuntimeException("Fail to login!");
        }

        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        String userId = loginUser.getBlogUser().getId().toString();
        String jwt = JWTUtils.createJWT(userId);

        redisTemplate.opsForValue().set("login:" + userId, JSON.toJSONString(loginUser),
                REDIS_EXPIRE_TIME, TimeUnit.MINUTES);

        return new BlogUserVO(blogUser, jwt);
    }

    @Override
    public void logout() {
        UsernamePasswordAuthenticationToken authenticationToken =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();

        LoginUser loginUser = (LoginUser) authenticationToken.getPrincipal();
        String userId = loginUser.getBlogUser().getId().toString();

        redisTemplate.delete("login:" + userId);
    }
}
