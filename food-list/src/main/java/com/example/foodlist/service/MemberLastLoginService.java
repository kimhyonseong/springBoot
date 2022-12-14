package com.example.foodlist.service;

import com.example.foodlist.domain.Member;
import com.example.foodlist.domain.MemberLastLogin;
import com.example.foodlist.repository.MemberLastLoginRepository;
import com.example.foodlist.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberLastLoginService {
    private final MemberLastLoginRepository lastLoginRepository;
    private final MemberRepository memberRepository;

    public void lastLoginRecoding(Member loginMember) {
        if (loginMember != null) {
            MemberLastLogin lastLogin;

            try {
                lastLogin = lastLoginRepository.findByMember(loginMember);

                if (lastLogin == null) {
                    lastLogin = new MemberLastLogin();

                    lastLogin.setMember(loginMember);
                    lastLogin.setMemberId(loginMember.getMemberId());
                    lastLogin.setLastLoginTime(LocalDateTime.now());
                }
                lastLogin.setLastLoginTime(LocalDateTime.now());
                lastLoginRepository.save(lastLogin);
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void changeDormancyMember() {
        LocalDateTime oneYearAgo = LocalDateTime.now().minusYears(1L);
        List<MemberLastLogin> lastLogin = lastLoginRepository.findAllByLastLoginTimeLessThanEqual(oneYearAgo);

        List<Member> members = new ArrayList<>();

        lastLogin.forEach(dormancyMember -> {
            Member member = dormancyMember.getMember();
            member.setState(50);
            members.add(member);
        });

        memberRepository.saveAll(members);
    }
}
