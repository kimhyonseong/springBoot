package com.example.foodpreference.controller;

import com.example.foodpreference.dto.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class MemberController {
    @GetMapping("login")
    public String loginView() {
        return "member/login";
    }

    @PostMapping("login")
    public String login(MemberDto memberDto) {
        log.info(memberDto.toString());

        // 로그인 로직
       return null;
    }
}
