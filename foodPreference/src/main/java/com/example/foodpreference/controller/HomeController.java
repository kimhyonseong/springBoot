package com.example.foodpreference.controller;

import com.example.foodpreference.dto.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
public class HomeController {

  @GetMapping("/")
  public String welcome() {
    return "pages/main";
  }

  @GetMapping("/main")
  public String test() {
    return "pages/main";
  }

  @GetMapping("/index")
  public String indexView() {
    return "index";
  }

  // 이쪽으로 들어가면 login 페이지로 가짐
  @RequestMapping("admin")
  public String test1() {

    return "admin";
  }

  @ResponseBody
  @RequestMapping("auth")
  public Authentication auth() {
    return SecurityContextHolder.getContext()
            .getAuthentication();
  }
}
