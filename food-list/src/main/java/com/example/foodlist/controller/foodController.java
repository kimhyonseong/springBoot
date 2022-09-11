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
    private final ReviewService reviewService;

    @GetMapping(value = {"foodList","/","foodList/{id}"})
    public String foodListPage(Model model, @PathVariable(required = false) String id) {
        List<Food> foodList = foodService.showAllFoods();
        model.addAttribute("foodList",foodList);
        model.addAttribute("foodId",id);
        return "common/foodList";
    }

    @PostMapping({"/food/review","/food/review/{reviewId}"})
    public String foodReview(HttpServletRequest request,
                             Model model,
                             @PathVariable(required = false) Long reviewId,
                             Review review, @RequestParam("foodId") Long foodId) {
        Food food = foodService.showFood(foodId);
        Cookie[] cookies = request.getCookies();
        String loginId = "";

        try {
            for (Cookie c : cookies) {
                if (Objects.equals(c.getName(), "loginId")) {
                    loginId = c.getValue();
                }
            }
        } catch (NullPointerException e) {
            Map<String, String> redirect = new HashMap<>();
            redirect.put("redirectUrl","/login");
            redirect.put("message","로그인이 필요합니다.");
            model.addAttribute("redirect",redirect);
            return "layout/redirect";
        }

        if (reviewId != null) {
            review.setIdx(reviewId);
        }

        review.setMemberId(loginId);
        int putResult = reviewService.putReview(food,loginId,review);

        return reviewService.returnResult(putResult,foodId, model);
    }
}
