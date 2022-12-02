package com.example.foodpreference.service;

import com.example.foodpreference.domain.Member;
import com.example.foodpreference.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemberDetailServiceTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberDetailService authService;

    @Test
    public void signUpTest() {
        Member member = new Member();
        member.setId("lss1545");
        member.setName("lss");
        member.setPassword("1234");
        member.setRole("USER");

        int result = memberService.signUp(member);
        assertEquals(300,result);

        Member member1 = new Member();
        member1.setId("lss01");
        member1.setName("lss");
        member1.setPassword("1234");
        member1.setRole("USER");

        int result1 = memberService.signUp(member1);
        System.out.println(member1);
        assertEquals(200,result1);

        Member member2 = memberRepository.findById("lss01").orElseThrow(()->new UsernameNotFoundException("no user"));
        System.out.println(member2);
    }

    @Test
    public void loginTest() {
        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();

        Member member = memberRepository.findById("lss1545").orElseThrow(()->new UsernameNotFoundException("no user"));
        member.setPassword(bCrypt.encode(member.getPassword()));

        System.out.println(authService.loadUserByUsername(member.getId()));
    }

    @Test
    void regText(){
        String str = "12341234&";

        assertTrue("aqweq".matches(".{4,10}"));
    }
}