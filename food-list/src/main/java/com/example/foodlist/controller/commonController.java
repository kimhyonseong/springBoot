package com.example.foodlist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class commonController {

    @RequestMapping("/redirect")
    public String redirect(HttpServletRequest request, Model model) {
        Map<String, String> redirect = new HashMap<>();
        redirect.put("redirectUrl", (String) request.getAttribute("redirectUrl"));
        redirect.put("message",(String) request.getAttribute("message"));
        model.addAttribute("redirect",redirect);
        return "layout/redirect";
    }
}
