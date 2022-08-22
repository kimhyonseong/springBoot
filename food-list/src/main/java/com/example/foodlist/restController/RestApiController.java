package com.example.foodlist.restController;

import com.example.foodlist.annotation.NoSpecial;
import com.example.foodlist.domain.Member;
import com.example.foodlist.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestApiController {
    private final MemberService memberService;

    @PostMapping("/rest/register")
    public ResponseEntity<Member> registerSuccess(
            @NoSpecial String signupId,
            String signupPw,
            String name,
            Model model
    ) {
        HttpHeaders headers = new HttpHeaders();
        Member member = new Member();
        member.setMemberId(signupId);
        member.setMemberPw(signupPw);
        member.setName(name);

        return ResponseEntity.ok().headers(headers).body(member);
    }
}
