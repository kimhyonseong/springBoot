package com.example.foodlist.clientConroller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class mainController {
    @RequestMapping("eat")
    public String mainView() {
        return "eat";
    }

    @RequestMapping("login")
    public String login_layout(Model model) {
        model.addAttribute("title","Login");
        model.addAttribute("mainClass","login");
        return "client/login";
    }

    @RequestMapping("test")
    public String main_layout() {
        return "client/main";
    }
}
