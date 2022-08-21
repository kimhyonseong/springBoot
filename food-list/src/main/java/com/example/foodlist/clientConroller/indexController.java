package com.example.foodlist.clientConroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class indexController {
    @RequestMapping("eat")
    public String mainView() {
        return "eat";
    }

    @RequestMapping("test")
    public String main_layout() {
        return "client/main";
    }

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("title","Main");
        return "index";
    }
}
