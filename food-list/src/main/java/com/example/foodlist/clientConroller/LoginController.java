package com.example.foodlist.clientConroller;

import com.example.foodlist.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final MemberService memberService;

    @GetMapping("login")
    public String loginPage(Model model) {
        model.addAttribute("title","Login");
        model.addAttribute("mainClass","login");
        return "client/login";
    }

    @PostMapping("login")
    public String loginProc(
            @RequestParam("loginId") String loginId,
            @RequestParam("loginPw") String loginPw,
            Model model) {

        // 로그인 시도
        int result = memberService.login(loginId,loginPw);
        // 실패 시 다시 로그인 페이지로
        if (result == -1) {
            model.addAttribute("error","아이디 또는 비밀번호가 잘못되었습니다.");
            return "client/login";
        }

        // 성공 시 전에 있던 페이지로 - 메인 페이지
        return "client/success";
    }
}
