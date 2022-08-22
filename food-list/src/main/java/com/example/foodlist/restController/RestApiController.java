package com.example.foodlist.restController;

import com.example.foodlist.annotation.NoSpecial;
import com.example.foodlist.domain.Member;
import com.example.foodlist.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class RestApiController {
    private final MemberService memberService;

    @PostMapping("/rest/register")
    public ResponseEntity<Member> registerSuccess(
            @Valid @RequestBody Member newMember
    ) {
        HttpHeaders headers = new HttpHeaders();
        Member member = new Member();
        member.setMemberId(newMember.getMemberId());
        member.setMemberPw(newMember.getMemberPw());
        member.setName(newMember.getName());

        return ResponseEntity.ok().headers(headers).body(member);
    }
}
