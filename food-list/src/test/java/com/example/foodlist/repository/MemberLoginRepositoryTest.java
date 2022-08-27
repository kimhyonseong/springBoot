package com.example.foodlist.repository;

import com.example.foodlist.domain.Member;
import com.example.foodlist.domain.MemberLogin;
import com.example.foodlist.service.MemberLastLoginService;
import com.example.foodlist.service.MemberLoginService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberLoginRepositoryTest {
    @Autowired
    MemberLoginService loginService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberLoginRepository memberLoginRepository;
    @Autowired
    MemberLoginService memberLoginService;

    @Test
    void login() {
        Member member = new Member();
        member.setMemberId("lss1545");
        member.setMemberPw("1234");
        member.setName("이순신");

        memberRepository.save(member);

        Member member1 = memberRepository.findByMemberId("lss1545");
        System.out.println("1st select "+member1);

        loginService.loginRecoding(member1,"127.0.0.1");
        memberRepository.save(member1);

        memberLoginRepository.findAll().forEach(System.out::println);
    }
}