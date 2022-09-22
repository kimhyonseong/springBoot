package com.example.foodlist.controller;

import com.example.foodlist.domain.Member;
import com.example.foodlist.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RegisterController {
    private final MemberService memberService;

    @PostMapping("/register")
    public String registerSuccess(@Validated Member member, BindingResult bindingResult, Model model) {
        Map<String,String> errorMap = new HashMap<>();
        Map<String,String> result = new HashMap<>();

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> {
                FieldError field = (FieldError) objectError;
                String message = objectError.getDefaultMessage();

                errorMap.put(field.getField()+"Error",message);
            });

            model.addAllAttributes(errorMap);
            model.addAttribute("Member", member);
            return "client/register";
        }

        try {
            String message = "이미 가입된 아이디 입니다.";
            List<Member> memberList = memberService.countId(member.getMemberId());

            if (memberList.size() > 0) {
                if (memberList.get(0).getState().equals(40)) {
                    message = "탈퇴한 계정은 30일 이후에 다시 가입할 수 있습니다.";
                }
                model.addAttribute("Member", member);

                result.put("redirectUrl","/register");
                result.put("message",message);
                model.addAttribute("redirect",result);

                return "layout/redirect";
            }
        } catch (RuntimeException e) {
            e.printStackTrace();

            result.put("redirectUrl","/register");
            result.put("message","에러가 발생하였습니다. 다시 시도해주세요.");
            model.addAttribute("redirect",result);

            return "layout/redirect";
        }

        memberService.put(member);
        return "client/registerSuccess";
    }

    @GetMapping("register")
    public String registerPage(Model model) {
        Member member = new Member();

        model.addAttribute("title","Sign Up");
        return "client/register";
    }
}
