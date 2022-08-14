package com.example.foodlist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TestController {
    @RequestMapping("/view")
    public String view1(Model model) {
        model.addAttribute("model","JSP");
        return "main";
    }
}
