package com.danyue.reactspringbootblogbackend.controller;

import com.danyue.reactspringbootblogbackend.entity.BlogUser;
import com.danyue.reactspringbootblogbackend.entity.BlogUserVO;
import com.danyue.reactspringbootblogbackend.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class LoginController {

    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping ("/login")
    public ResponseEntity<BlogUserVO> login(@RequestBody BlogUser blogUser) {
        return ResponseEntity.ok().body(loginService.login(blogUser));
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout() {
        loginService.logout();

        return ResponseEntity.ok("Logged out successfully!");
    }

    @GetMapping("/checkToken")
    public ResponseEntity<String> checkToken() {
        return ResponseEntity.ok("Token is valid!");
    }
}
