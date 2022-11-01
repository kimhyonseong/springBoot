package com.example.foodpreference.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MemberController {
    @GetMapping("login")
    public String loginView() {
        return "member/login";
    }

    @PostMapping("login")
    public String login() {
        // 로그인 로직
       return null;
    }
}
