package com.example.foodpreference.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

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
}
