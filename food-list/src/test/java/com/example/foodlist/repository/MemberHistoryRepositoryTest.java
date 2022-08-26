package com.example.foodlist.repository;

import com.example.foodlist.domain.Member;
import com.example.foodlist.domain.MemberHistory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberHistoryRepositoryTest {
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberHistoryRepository memberHistoryRepository;

    @Test
    void memberHistorySaveTest() {
        Member member = new Member();

        member.setName("이순신");
        member.setMemberId("lss1545");
        member.setMemberPw("1234");

        memberRepository.save(member);

        List<Member> members = memberRepository.findAll();
        System.out.println(members);

        List<MemberHistory> memberHistoryList = memberHistoryRepository.findAll();
        System.out.println(memberHistoryList);
    }

    @Test
    void memberHistoryStateTest() {
        Member member = new Member();

        member.setName("이순신");
        member.setMemberId("lss1545");
        member.setMemberPw("1234");

        memberRepository.save(member);

        List<MemberHistory> memberHistoryList = memberHistoryRepository.findAll();
        System.out.println(memberHistoryList);

        // 20 : 비밀번호 변경
        Member member1 = memberRepository.findByMemberId("lss1545");
        System.out.println(member1);
        member1.setMemberPw("4321");
        memberRepository.save(member1);
        System.out.println(member1);
        memberHistoryRepository.findAll().forEach(System.out::println);

        // 50 : 휴면 계정 변경
        Member member2 = memberRepository.findByMemberId("lss1545");
        member2.setState(50);
        memberRepository.save(member2);
        System.out.println(member2);
        memberHistoryRepository.findAll().forEach(System.out::println);
    }
}