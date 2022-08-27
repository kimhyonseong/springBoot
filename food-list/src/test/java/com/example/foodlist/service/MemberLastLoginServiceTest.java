package com.example.foodlist.service;

import com.example.foodlist.domain.Member;
import com.example.foodlist.domain.MemberLastLogin;
import com.example.foodlist.repository.MemberLastLoginRepository;
import com.example.foodlist.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberLastLoginServiceTest {
    @Autowired
    MemberLastLoginService lastLoginService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberLastLoginRepository lastLoginRepository;

    @Test
    void lastLoginRecode() {
        Member member = new Member();
        member.setMemberId("lss1545");
        member.setMemberPw("1234");
        member.setName("이순신");

        memberRepository.save(member);

        Member member1 = memberRepository.findByMemberId("lss1545");
        System.out.println("member1 : "+ member1);
        lastLoginService.lastLoginRecoding(member1);
        System.out.println("login 1-------------------------");
        lastLoginRepository.findAll().forEach(System.out::println);

        Member member2 = memberRepository.findByMemberId("lss1545");
        lastLoginService.lastLoginRecoding(member2);
        System.out.println("login 2-------------------------");
        lastLoginRepository.findAll().forEach(System.out::println);
    }

    @Test
    void dormancyTest(){
        Member member = new Member();
        member.setMemberId("lss1545");
        member.setMemberPw("1234");
        member.setName("이순신");
        memberRepository.save(member);

        Member member1 = memberRepository.findByMemberId("lss1545");
        lastLoginService.lastLoginRecoding(member1);

        MemberLastLogin lastLogin = lastLoginRepository.findByMember(member1);
        lastLogin.setLastLoginTime(LocalDateTime.now().minusYears(1L));
        System.out.println("lastLogin : "+lastLogin);
        lastLoginRepository.save(lastLogin);

        lastLoginService.changeDormancyMember();

        Member result = memberRepository.findByMemberId("lss1545");
        System.out.println("result = "+result);
    }
}