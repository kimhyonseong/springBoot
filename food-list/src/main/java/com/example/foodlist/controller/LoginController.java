package com.example.foodlist.controller;

import com.example.foodlist.domain.Member;
import com.example.foodlist.service.MemberLastLoginService;
import com.example.foodlist.service.MemberLoginService;
import com.example.foodlist.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final MemberLastLoginService lastLoginService;
    private final MemberService memberService;
    private final MemberLoginService loginService;

    @GetMapping("login")
    public String loginPage(@RequestParam(value = "redirect", required = false)  String redirect,
                            Model model) {
        model.addAttribute("redirect",redirect);
        return "client/login";
    }

    @PostMapping("login")
    public String loginProc(
            Model model,
            Member member,
            BindingResult bindingResult,
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "redirect", required = false) String redirect) {
        String failPath = "/login";
        String successPath = "common/foodList";
        String message = "에러가 발생하였습니다. 다시 시도해주십시오.";
        Member loginMember;

        if (!Objects.equals(redirect, "")) {
            successPath = "redirect:"+redirect;
        } else {
            successPath = "redirect:/foodList";
        }

        Map<String,String> result = new HashMap<>();
        result.put("redirectUrl",failPath);
        result.put("message",message);

        // 폼 데이터 에러 시
        if (bindingResult.hasErrors()) {
            return "layout/redirect";
        }

        // 로그인 시도
        try {
            loginMember = memberService.login(member.getMemberId(), member.getMemberPw());
        } catch (Exception e) {
            return "layout/redirect";
        }

        // 실패 시 다시 로그인 페이지로
        if (loginMember == null) {
            message = "아이디 또는 비밀번호가 일치하지 않습니다.";
        } else if (loginMember.getState().equals(30)) {
            message = "로그인이 정지된 계정입니다. 관리자에게 문의하세요.";
        } else if (loginMember.getState().equals(40)) {
            message = "탈퇴 계정입니다. 탈퇴계정은 30일 이후에 삭제됩니다.";
        } else if (loginMember.getState().equals(50)) {
            message = "휴면 계정입니다. 관리자에게 문의하세요.";
        } else {
            lastLoginService.lastLoginRecoding(loginMember);
            loginService.loginRecoding(loginMember,request.getRemoteAddr());
            loginService.loginCookie(loginMember,response);

            // 성공 시 전에 있던 페이지로 - 메인 페이지
            return successPath;
        }

        result.put("message",message);
        model.addAttribute("redirect",result);

        return "layout/redirect";
    }

    @PostMapping("/logout")
    public String logout(HttpServletResponse response,Model model,
                         @RequestParam(required = false) String redirect) {
        Cookie userId = new Cookie("loginId",null);
        Cookie userName = new Cookie("loginName",null);

        userId.setMaxAge(0);
        userName.setMaxAge(0);

        response.addCookie(userId);
        response.addCookie(userName);

        if (redirect == null) {
            redirect = "/foodList";
        }

        Map<String,String> result = new HashMap<>();
        result.put("redirectUrl",redirect);
        result.put("message","로그아웃 되었습니다.");

        model.addAttribute("redirect",result);

        return "layout/redirect";
    }
}
