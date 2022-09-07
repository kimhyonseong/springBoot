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
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class RegisterController {
    private final MemberService memberService;

    @PostMapping("/register")
    public String registerSuccess(@Validated Member member, BindingResult bindingResult, Model model) {
        Map<String,String> errorMap = new HashMap<>();

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

        if (memberService.countId(member.getMemberId()) > 0) {
            model.addAttribute("Member", member);
            return "client/register";
        }

        memberService.put(member);
        return "client/registerSuccess";
    }

    @GetMapping("register")
    public String registerPage(Model model) {
        Member member = new Member();

        model.addAttribute("title","Sign Up");
        model.addAttribute("Member",member);
        return "client/register";
    }
}
