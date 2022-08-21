package com.example.foodlist.service;

import com.example.foodlist.domain.Member;
import com.example.foodlist.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        member.setMemberId("!@khs6524");
        member.setMemberPw("1234");

        Member member1 = new Member();
        member1.setName("이순신");
        member1.setMemberId("lss1545");
        member1.setMemberPw("1234");

        Member member2 = new Member();
        member2.setName("이순신");
        member2.setMemberId("khs6524");
        member2.setMemberPw("1234");

        Member member3 = new Member();
        member3.setName("이순신");

        Member member4 = new Member();
        member4.setMemberId("lss1545");

        System.out.println("!@khs6524 put : "+memberService.put(member));
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

        System.out.println("null member : "+countId(null));
        System.out.println("special str member : "+countId("khs\\1"));
        System.out.println("no member : "+countId("khs"));
        System.out.println("already member : "+countId("khs6524"));
    }

    private int countId(String memberId) {
        List<Member> count = null;

        try {
//            if (!this.memberIdTest(memberId)) {
//                throw new RuntimeException("memberId pattern is wrong");
//            }
            count = memberRepository.findByMemberId(memberId);

            return count.size();
        } catch (RuntimeException e) {
            return -1;
        }
    }

    private Boolean memberIdTest(String memberId) {
        Pattern pattern = Pattern.compile("^[\\da-zA-Z]+$");

        try {
            if (memberId == null || memberId.equals("")) {
                System.out.println("this is empty");
                throw new Exception("id is empty");
            }
            Matcher matcher = pattern.matcher(memberId);
            System.out.println(memberId+" is "+matcher.matches());
            return matcher.matches();
        } catch (Exception e) {
            return false;
        }
    }

    @Test
    void memberIdTest() {
        Boolean idTest = this.memberIdTest("khs6524");
        Boolean idTest1 = this.memberIdTest("khs6524!");
        Boolean idTest2 = this.memberIdTest("khs 6524");

        assertEquals(idTest,true);
        assertEquals(idTest1,false);
        assertEquals(idTest2,false);
    }


    @Test
    void login() {
        Member member1 = new Member();
        member1.setMemberId("khs6524");
        member1.setMemberPw("1234");
        member1.setName("김현성");

        memberRepository.save(member1);
        memberRepository.flush();

        try {
            Member member2 = memberRepository.findByMemberIdAndMemberPw("khs6524","1234");
            System.out.println(member2.getMemberId());
            System.out.println("success");
        } catch (RuntimeException e) {
            System.out.println("error");
        }

        try {
            Member member3 = memberRepository.findByMemberIdAndMemberPw("khs6524","123");
            System.out.println(member3.getMemberId());
            System.out.println("success");
        } catch (RuntimeException e) {
            System.out.println("error");
        }

        try {
            Member member4 = memberRepository.findByMemberIdAndMemberPw(null,"");
            System.out.println(member4.getMemberId());
            System.out.println("success");
        } catch (RuntimeException e) {
            System.out.println("error");
        }
    }

    @Test
    void matchTest() {
        String value= "khs652.4";
        String pattern = "[ !@#$%^&*\\(\\)\\-\\.\\,\\\\_+=|\\/\\[\\]\\<\\>\\?'\"`~]";
        //pattern = "[!@#]";

        System.out.println(value);
        System.out.println(!Pattern.compile(pattern).matcher(value).find());
    }
}