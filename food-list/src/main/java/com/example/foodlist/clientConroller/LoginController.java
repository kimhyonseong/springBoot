package com.example.foodlist.clientConroller;

import com.example.foodlist.domain.Member;
import com.example.foodlist.service.MemberLoginService;
import com.example.foodlist.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class LoginController {
    private final MemberService memberService;
    private final MemberLoginService memberLoginService;

    @GetMapping("login")
    public String loginPage() {
        return "client/login";
    }

    @PostMapping("login")
    public String loginProc(
            Member member,
            BindingResult bindingResult,
            HttpServletResponse response) {
        String failPath = "client/login";
        String successPath = "client/success";
        Member loginMember;

        response.setContentType("text/html; charset=utf-8");

        // 폼 데이터 에러 시
        if (bindingResult.hasErrors()) {
            alertMsg(response,"에러가 발생하였습니다. 다시 시도해주십시오.");
            return failPath;
        }

        // 로그인 시도
        try {
            loginMember = memberService.login(member.getMemberId(), member.getMemberPw(), response);
        } catch (Exception e) {
            return failPath;
        }

        // 실패 시 다시 로그인 페이지로
        if (loginMember == null) {
            alertMsg(response,"아이디 또는 비밀번호가 일치하지 않습니다.");
            return failPath;
        } else {
            memberLoginService.lastLoginRecoding(loginMember);
            memberLoginService.loginCookie(loginMember,response);
        }

        // 성공 시 전에 있던 페이지로 - 메인 페이지
        return successPath;
    }

    private void alertMsg(HttpServletResponse response,String msg) {
        try {
            response.getWriter().println("<script>alert('"+msg+"')</script>");
            response.getWriter().flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
