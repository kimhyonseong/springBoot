package com.example.foodpreference.service;

import com.example.foodpreference.domain.Member;
import com.example.foodpreference.dto.MemberDto;
import com.example.foodpreference.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    // 안씀
    public boolean login(MemberDto memberDto, HttpServletResponse response) {
        try {
            Member member = memberRepository.findById(memberDto.getId());

            if (member.getPassword().equals(memberDto.getPassword())) {
                Cookie memberCookie = new Cookie("member", memberDto.getId());
                memberCookie.setMaxAge(600);
                memberCookie.setPath("/");

                response.addCookie(memberCookie);
            } else {
                return false;
            }
            return true;
        } catch (RuntimeException e) {
            return false;
        }
    }

    // 안씀
    public boolean logout(HttpServletResponse response) {
        try {
            Cookie memberCookie = new Cookie("member", null);
            memberCookie.setPath("/");
            memberCookie.setMaxAge(0);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Transactional
    public int signUp(Member member) {
        try {
            member.setPassword(passwordEncoder.encode(member.getPassword()));
            memberRepository.save(member);
            return 200;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return 400;
        }
    }

    @Transactional(readOnly = true)
    public boolean checkMemberIdDuplication(String id) {
        return memberRepository.existsById(id);
    }
}
