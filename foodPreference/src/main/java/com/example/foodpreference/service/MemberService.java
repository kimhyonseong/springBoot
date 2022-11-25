package com.example.foodpreference.service;

import com.example.foodpreference.domain.Member;
import com.example.foodpreference.dto.MemberDto;
import com.example.foodpreference.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
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

    public boolean isLogin(@AuthenticationPrincipal User user) {
        try {
            if (user == null) {
                throw new IllegalStateException();
            }
            Member member = memberRepository.findById(user.getUsername());

            if (member == null) {
                throw new IllegalStateException();
            }
        } catch (RuntimeException e) {
            return false;
        }
        return true;
    }
}
