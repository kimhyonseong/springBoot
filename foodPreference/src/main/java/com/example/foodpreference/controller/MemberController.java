package com.example.foodpreference.controller;

import com.example.foodpreference.domain.Member;
import com.example.foodpreference.dto.MemberDto;
import com.example.foodpreference.service.MemberDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberDetailService memberDetailService;

    @GetMapping("login")
    public String loginView() {
        return "member/login";
    }

//    @PostMapping("login")
//    public String login(@RequestBody MemberDto memberDto) {
//        System.out.println(memberDto);
//        System.out.println("--------------------");
//        log.info(memberDto.toString());
//
//        // 로그인 로직
//       return "main";
//    }
    @PostMapping("member")
    public String join(@RequestBody MemberDto memberDto) {
        System.out.println(memberDto);
        System.out.println("--------------------");
        log.info(memberDto.toString());

        Member member = new Member();
        member.setName(memberDto.getName());
        member.setId(memberDto.getId());
        member.setPassword(memberDto.getPassword());

        switch (memberDetailService.signUp(member)) {
            case 200 : return "joinSuccess";
            case 300 : return "main";
            default: return "fail";
        }
    }
}
