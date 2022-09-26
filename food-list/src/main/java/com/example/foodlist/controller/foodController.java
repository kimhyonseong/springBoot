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
    public String foodListPage(Model model, @PathVariable(required = false) String id,
                               @RequestParam(value = "food", required = false) String foodName) {
        List<Food> foodList = foodService.showAllFoods();

        if (foodName != null) {
            foodList = foodService.searchFood(foodName);
        }
        model.addAttribute("foodList",foodList);
        model.addAttribute("foodId",id);
        model.addAttribute("foodName",foodName);
        return "common/foodList";
    }
}
