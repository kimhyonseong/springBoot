package com.example.foodlist.controller;

import com.example.foodlist.domain.Food;
import com.example.foodlist.domain.Review;
import com.example.foodlist.service.FoodService;
import com.example.foodlist.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class foodController {
    private final FoodService foodService;

    @GetMapping(value = {"foodList","/","foodList/{id}"})
    public String foodListPage(Model model, @PathVariable(required = false) String id) {
        List<Food> foodList = foodService.showAllFoods();
        model.addAttribute("foodList",foodList);
        model.addAttribute("foodId",id);
        return "common/foodList";
    }
}
