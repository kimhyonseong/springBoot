package com.example.foodlist.service;

import com.example.foodlist.domain.Member;
import com.example.foodlist.domain.MemberLogin;
import com.example.foodlist.repository.MemberLoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

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
}
