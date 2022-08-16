package com.example.foodlist.service;

import com.example.foodlist.domain.Member;
import com.example.foodlist.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;

    @Test
    void putTest() {
        Member member = new Member();
        member.setName("김현성");
        member.setMemberId("khs6524");

        Member member1 = new Member();
        member1.setName("이순신");
        member1.setMemberId("lss1545");

        Member member2 = new Member();
        member2.setName("이순신");
        member2.setMemberId("khs6524");

        Member member3 = new Member();
        member3.setName("이순신");

        Member member4 = new Member();
        member4.setMemberId("lss1545");

        System.out.println("khs6524 put : "+memberService.put(member));
        System.out.println("lss1545 put : "+memberService.put(member1));
        System.out.println("khs6524 2nd put : "+memberService.put(member2));
        System.out.println("no id put : "+memberService.put(member3));
        System.out.println("no name put : "+memberService.put(member4));
    }

    @Test
    void alreadyIdTest() {
        Member member = new Member();
        member.setName("김현성");
        member.setMemberId("khs6524");

        memberRepository.save(member);
        memberRepository.flush();

        System.out.println("null member : "+memberService.countId(null));
        System.out.println("no member : "+memberService.countId("khs"));
        System.out.println("already member : "+memberService.countId("khs6524"));
    }
}