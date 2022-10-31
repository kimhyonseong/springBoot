package com.example.foodpreference.controller;

import com.example.foodpreference.dto.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
public class HomeController {
  @GetMapping("/login")
  public String loginView() {
    return "login";
  }

  @GetMapping("/main")
  public String test() {
    return "main";
  }

  // 이쪽으로 들어가면 login 페이지로 가짐
  @GetMapping("/admin")
  public String test1() {
    return "main";
  }

  @PostMapping("/loginProc")
  public String proc(MemberDto memberDto) {
    log.info(memberDto.toString());
    return "loginProc";
  }
}
