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
        memberRepository.flush();

        List<Member> members = memberRepository.findAll();
        System.out.println(members);

//        MemberHistory memberHistory = new MemberHistory();
//
//        memberHistory.setMember(members.get(0));
//        memberHistory.setMemberId(members.get(0).getMemberId());
//        memberHistory.setName(members.get(0).getName());
//
//        memberHistoryRepository.save(memberHistory);
//        memberHistoryRepository.flush();

        List<MemberHistory> memberHistoryList = memberHistoryRepository.findAll();
        System.out.println(memberHistoryList);
    }
}