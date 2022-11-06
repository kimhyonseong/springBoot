package com.example.foodpreference.controller;

import com.example.foodpreference.domain.Member;
import com.example.foodpreference.dto.MemberDto;
import com.example.foodpreference.service.MemberDetailService;
import com.example.foodpreference.validator.CheckIdValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberDetailService memberDetailService;
    private final CheckIdValidator checkIdValidator;

    @GetMapping("login")
    public String loginView() {
        return "member/login";
    }

    @GetMapping("join")
    public String join() {
        return "member/join";
    }

    @PostMapping("join")
    public String join(@Validated @ModelAttribute MemberDto memberDto, HttpServletRequest request,
                       Model model, BindingResult bindingResult, Errors errors) {

        System.out.println("join?!");

        if (errors.hasErrors()) {
            System.out.println("error");
            model.addAttribute("memberDto",memberDto);

            Map<String,String> map = new HashMap<>();

            for (FieldError error : bindingResult.getFieldErrors()) {
                map.put("valid_"+error.getField(),error.getDefaultMessage());
                log.info("error message : "+error.getDefaultMessage());
            }

            return "member/join";
        } else {
            Member member = new Member();
            member.setName(memberDto.getName());
            member.setId(memberDto.getId());
            member.setPassword(memberDto.getPassword());

            Map<String, String> map = new HashMap<>();
            map.put("id", memberDto.getId());
            map.put("name", memberDto.getName());
            map.put("password", memberDto.getPassword());

            switch (memberDetailService.signUp(member)) {
                case 200:
                    return "member/joinSuccess";
                case 300:
                    map.put("message", "중복된 아이디 입니다.");

                    model.addAttribute("member", map);

                    return "redirect:/join";
                case 400:
                    map.put("message", "에러가 발생하였습니다. 잠시후 다시 시도해주세요.");

                    model.addAllAttributes(map);

                    return "forward:/join";
                default:
                    return "main";
            }
        }
    }

    @InitBinder
    public void validatorBinder(WebDataBinder binder) {
        System.out.println("validator in");
        binder.addValidators(checkIdValidator);
        System.out.println("다음.");
    }

    @GetMapping("/join/{id}/exist")
    public ResponseEntity<Boolean> checkId(@PathVariable String id) {
        return ResponseEntity.ok(memberDetailService.checkMemberIdDuplication(id));
    }

    @RequestMapping("fail")
    public String failPage() {
        return "fail";
    }
}
