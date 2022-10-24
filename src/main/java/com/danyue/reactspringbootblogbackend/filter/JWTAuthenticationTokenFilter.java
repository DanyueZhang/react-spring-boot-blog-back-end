package com.danyue.reactspringbootblogbackend.filter;

import com.alibaba.fastjson.JSON;
import com.danyue.reactspringbootblogbackend.entity.LoginUser;
import com.danyue.reactspringbootblogbackend.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JWTAuthenticationTokenFilter extends OncePerRequestFilter {

    private final RedisTemplate<String, String> redisTemplate;

    @Autowired
    public JWTAuthenticationTokenFilter(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String token = request.getHeader("token");

        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);

            return;
        }

        String userId;

        try {
            userId = JWTUtils.parseJWT(token);
        } catch (Exception e) {
            throw new RuntimeException("Invalid token!");
        }

        LoginUser loginUser =
                JSON.parseObject(redisTemplate.opsForValue().get("login:" + userId), LoginUser.class);

        if (Objects.isNull(loginUser)) {
            throw new RuntimeException("Not logged in!");
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginUser, null, loginUser.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        filterChain.doFilter(request, response);
    }
}
