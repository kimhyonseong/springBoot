package com.example.foodlist.controller;

import com.example.foodlist.domain.Food;
import com.example.foodlist.domain.Review;
import com.example.foodlist.service.FoodService;
import com.example.foodlist.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class reviewController {
    private final FoodService foodService;
    private final ReviewService reviewService;

    @PostMapping({"/food/review"})
    public String foodReview(HttpServletRequest request,
                             Model model,
                             Review review, @RequestParam("foodId") Long foodId) {
        Food food = foodService.showFood(foodId);
        Cookie[] cookies = request.getCookies();
        String loginId = "";
        Long reviewId = 0L;

        try {
            for (Cookie c : cookies) {
                if (Objects.equals(c.getName(), "loginId")) {
                    loginId = c.getValue();
                    reviewId = reviewService.findReviewId(foodId,loginId);
                }
            }
        } catch (NullPointerException e) {
            Map<String, String> redirect = new HashMap<>();
            redirect.put("redirectUrl","/login");
            redirect.put("message","로그인이 필요합니다.");
            model.addAttribute("redirect",redirect);
            return "layout/redirect";
        }

        if (reviewId != 0L) {
            review.setIdx(reviewId);
        }

        review.setMemberId(loginId);
        int putResult = reviewService.putReview(food,loginId,review);

        return reviewService.returnResult(putResult,foodId, model);
    }
}
