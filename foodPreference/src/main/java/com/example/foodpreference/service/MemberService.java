package com.example.foodpreference.service;

import com.example.foodpreference.domain.Member;
import com.example.foodpreference.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
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

            memberRepository.findById(user.getUsername()).orElseThrow(()->new UsernameNotFoundException("no login"));
        }
        catch (UsernameNotFoundException e) {
            log.error("user is null");
            return false;
        } catch (RuntimeException e) {
            log.error("isLogin error - no login");
            return false;
        }
        return true;
    }
}
