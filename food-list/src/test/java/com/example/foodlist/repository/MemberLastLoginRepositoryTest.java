package com.example.foodlist.repository;

import com.example.foodlist.domain.Member;
import com.example.foodlist.domain.MemberLastLogin;
import com.example.foodlist.domain.MemberLogin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberLastLoginRepositoryTest {
    @Autowired
    MemberLastLoginRepository memberLastLoginRepository;
    @Autowired
    MemberLoginRepository memberLoginRepository;
    @Autowired
    MemberRepository memberRepository;

    @Test
    void LastLoginTest() {
        Member member = new Member();
        member.setName("이순신");
        member.setMemberId("lss1545");
        member.setMemberPw("1234");

        memberRepository.save(member);

        Member saveMember = memberRepository.findByMemberId("lss1545");

        MemberLogin memberLogin = new MemberLogin();
        memberLogin.setMember(saveMember);
        memberLogin.setMemberId(saveMember.getMemberId());
        memberLogin.setLoginDate(LocalDateTime.now());
        memberLoginRepository.save(memberLogin);

        MemberLastLogin memberLastLogin = new MemberLastLogin();
        memberLastLogin.setMember(saveMember);
        memberLastLogin.setMemberId(saveMember.getMemberId());
        memberLastLogin.setLastLoginTime(LocalDateTime.now());
        memberLastLoginRepository.save(memberLastLogin);

        memberLastLogin.setLastLoginTime(LocalDateTime.now());
        memberLastLoginRepository.save(memberLastLogin);

        memberLastLogin.setLastLoginTime(LocalDateTime.now());
        memberLastLoginRepository.save(memberLastLogin);

        memberLoginRepository.findAll().forEach(System.out::println);
        memberLastLoginRepository.findAll().forEach(System.out::println);
    }
}