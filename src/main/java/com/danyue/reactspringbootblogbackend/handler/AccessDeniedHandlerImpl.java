package com.danyue.reactspringbootblogbackend.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        try {
            response.setStatus(403);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print("Insufficient authorization!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
