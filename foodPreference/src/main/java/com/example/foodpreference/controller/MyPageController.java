package com.example.foodpreference.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

  @GetMapping("myPage/order")
  public String orderPage(Pageable pageable, Model model) {
    model.addAttribute("page",pageable.getPageNumber());
    return "/myPage/order";
  }
}