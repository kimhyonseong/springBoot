package com.example.foodlist.service;

import com.example.foodlist.domain.Member;
import com.example.foodlist.domain.MemberLastLogin;
import com.example.foodlist.repository.MemberLastLoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MemberLastLoginService {
    private final MemberLastLoginRepository lastLoginRepository;

    public void lastLoginRecoding(Member member) {
        if (member != null) {
            MemberLastLogin lastLogin = new MemberLastLogin();
            lastLogin.setMemberId(member.getMemberId());
            lastLogin.setMember(member);
            lastLogin.setLastLoginTime(LocalDateTime.now());

            lastLoginRepository.save(lastLogin);
        }
    }
}
