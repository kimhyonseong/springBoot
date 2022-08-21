package com.example.foodlist.clientConroller;

import com.example.foodlist.domain.Member;
import com.example.foodlist.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegisterController {
    private final MemberService memberService;

    @PostMapping("/register")
    public String registerSuccess(
            String signupId,
            String signupPw,
            String name,
            Model model
    ) {
        Member member = new Member();
        member.setMemberId(signupId);
        member.setMemberPw(signupPw);
        member.setName(name);

        System.out.println(member);

        model.addAttribute("signupId",signupId);
        model.addAttribute("name",name);

        try {
            int result = memberService.put(member);

            switch (result) {
                case 0:
                    model.addAttribute("mainClass","login");
                    return "client/registerSuccess";
                case -1:
                    throw new RuntimeException("error");
                default:
                    model.addAttribute("error","duplicate ID");
                    model.addAttribute("errorMsg","이미 존재하는 아이디");

                    return "client/register";
            }
        } catch (RuntimeException e) {
            model.addAttribute("errorMsg","error");
            return "client/register";
        }
    }

    @GetMapping("register")
    public String registerPage(Model model) {
        model.addAttribute("title","Sign Up");
        model.addAttribute("mainClass","login");
        return "client/register";
    }
}
