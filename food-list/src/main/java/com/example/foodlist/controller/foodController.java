package com.example.foodlist.controller;

import com.example.foodlist.domain.Food;
import com.example.foodlist.domain.Review;
import com.example.foodlist.service.FoodService;
import com.example.foodlist.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class foodController {
    private final FoodService foodService;
    private final ReviewService reviewService;

    @GetMapping({"foodList","/"})
    public String foodListPage(Model model) {
        List<Food> foodList = foodService.showAllFoods();
        model.addAttribute("foodList",foodList);
        return "common/foodList";
    }

    @GetMapping("foodList/{id}")
    public String foodListPage(Model model, @PathVariable String id) {
        List<Food> foodList = foodService.showAllFoods();
        model.addAttribute("foodList",foodList);
        model.addAttribute("foodId",id);
        return "common/foodList";
    }

    @PostMapping("/food/review")
    public String foodReview(HttpServletRequest request, Model model,
                             Review review, @RequestParam("foodId") Long foodId) {
        Food food = foodService.showFood(foodId);
        Cookie[] cookies = request.getCookies();
        String loginId = "";

        for(Cookie c : cookies) {
            if (Objects.equals(c.getName(), "loginId")) {
                loginId = c.getValue();
            }
        }

        review.setMemberId(loginId);
        int putResult = reviewService.putReview(food,loginId,review);

        return reviewService.returnResult(putResult,foodId, model);
    }
}
