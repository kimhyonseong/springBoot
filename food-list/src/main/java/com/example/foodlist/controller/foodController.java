package com.example.foodlist.controller;

import com.example.foodlist.domain.Food;
import com.example.foodlist.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
