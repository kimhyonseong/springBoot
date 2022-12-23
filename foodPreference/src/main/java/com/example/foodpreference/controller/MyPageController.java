package com.example.foodpreference.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MyPageController {
  @GetMapping("/myPage")
  public String myPage() {
    return "/myPage/index";
  }

  @GetMapping("myPage/cart")
  public String cartPage() {
    return "/myPage/cart";
  }
}