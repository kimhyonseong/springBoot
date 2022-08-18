package com.example.foodlist.clientConroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class mainController {
    @RequestMapping("eat")
    public String mainView() {
        return "eat";
    }

    @RequestMapping("login")
    public String login_layout() {
        return "login_layout";
    }

    @RequestMapping("test")
    public String main_layout() {
        return "main";
    }
}
