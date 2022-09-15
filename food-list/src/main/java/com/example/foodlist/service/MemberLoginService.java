package com.example.foodlist.service;

import com.example.foodlist.domain.Member;
import com.example.foodlist.domain.MemberLogin;
import com.example.foodlist.repository.MemberLoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MemberLoginService {
    private final MemberLoginRepository memberLoginRepository;

    public void loginRecoding(Member member,String ip) {
        MemberLogin memberLogin = new MemberLogin();
        memberLogin.setMember(member);
        memberLogin.setMemberId(member.getMemberId());
        memberLogin.setLoginDate(LocalDateTime.now());
        memberLogin.setIp(ip);

        memberLoginRepository.save(memberLogin);
    }

    public void loginCookie(Member member, HttpServletResponse response) {
        if (member != null) {
            Cookie loginId = new Cookie("loginId", member.getMemberId());
            Cookie loginName = new Cookie("loginName", member.getName());

            response.addCookie(loginId);
            response.addCookie(loginName);
        }
    }

    public Map<String,String> loginCheck(HttpServletRequest request, Model model) {
        HashMap<String,String> result = new HashMap<>();
        Cookie[] cookies = request.getCookies();

        try {
            for (Cookie c : cookies) {
                if (Objects.equals(c.getName(),"loginId")) {
                    result.put("loginId",c.getValue());
                    break;
                }
            }
            result.put("error",null);
        } catch (NullPointerException e) {
            System.out.println("로그인 안되어 있음");
            e.printStackTrace();

            Map<String, String> redirect = new HashMap<>();
            redirect.put("redirectUrl","/login");
            redirect.put("message","로그인이 필요합니다.");
            model.addAttribute("redirect",redirect);

            result.put("error","no login");
            result.put("result","layout/redirect");
        } catch (RuntimeException e) {
            e.printStackTrace();
            Map<String, String> redirect = new HashMap<>();
            redirect.put("redirectUrl","/");
            redirect.put("message","에러가 발생하였습니다.");
            model.addAttribute("redirect",redirect);

            result.put("error","runtime error");
            result.put("result","layout/redirect");
        }

        return result;
    }
}
