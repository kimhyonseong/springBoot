package com.example.foodlist.repository;

import com.example.foodlist.domain.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    void memberSave() {
        Member member = new Member();
        member.setMemberId("khs1");
        member.setMemberPw("1234");
        member.setName("test");

        System.out.println(member);
        memberRepository.save(member);

        List<Member> member1 = memberRepository.findAll();
        System.out.println(member1);
    }

    @Test
    void memberDelete() {
        Member member1 = memberRepository.findByMemberId("lss5");
        System.out.println("member1 : "+member1);
        List<Member> memberList = memberRepository.findAllByStateAndUpdDateBefore(90, LocalDateTime.now().minusDays(30));
        System.out.println("memberList : "+memberList);
        memberRepository.deleteAllInBatch(memberList);

        Member member2 = memberRepository.findByMemberId("lss5");
        System.out.println("member2 : "+member2);
    }
}