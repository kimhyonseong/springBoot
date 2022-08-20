package com.example.foodlist.clientConroller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

public class LoginController {
    @RequestMapping("login")
    public String loginPage(Model model) {
        model.addAttribute("title","Login");
        model.addAttribute("mainClass","login");
        return "client/login";
    }

    @PostMapping("login")
    public String loginProc() {
        // 로그인 시도

        // 실패 시 다시 로그인 페이지로

        // 성공 시 전에 있던 페이지로 - 메인 페이지
        return "client/success";
    }
}
