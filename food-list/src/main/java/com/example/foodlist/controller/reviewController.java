package com.example.foodlist.controller;

import com.example.foodlist.domain.Food;
import com.example.foodlist.domain.Review;
import com.example.foodlist.service.FoodService;
import com.example.foodlist.service.MemberLoginService;
import com.example.foodlist.service.MemberService;
import com.example.foodlist.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class reviewController {
    private final FoodService foodService;
    private final MemberLoginService memberLoginService;
    private final ReviewService reviewService;

    @PostMapping({"/food/review"})
    public String insertReview(HttpServletRequest request,
                             Model model,
                             Review review, @RequestParam("foodId") Long foodId) {
        Map<String, String> login = memberLoginService.loginCheck(request, model);
        Food food = foodService.showFood(foodId);

        if (!Objects.equals(login.get("error"), null)) {
            return login.get("result");
        }

        int putResult = reviewService.putReview(food,login.get("loginId"),review);

        return reviewService.returnResult(putResult,foodId, model);
    }

    @PutMapping("/food/review/{reviewIdx}")
    public String updateReview(HttpServletRequest request,
                               Model model,
                               Review updateReview,
                               @RequestParam("foodId") Long foodId,
                               @PathVariable Long reviewIdx) {
        Review review = reviewService.findReview(reviewIdx);
        review.setScore(updateReview.getScore());
        review.setComment(updateReview.getComment());

        return insertReview(request,model,review, foodId);
    }

    @DeleteMapping("/food/review/{reviewIdx}")
    public String deleteReview(HttpServletRequest request,
                               Model model,
                               Review deleteReview,
                               @RequestParam("foodId") Long foodId,
                               @PathVariable Long reviewIdx) {
        Map<String, String> login = memberLoginService.loginCheck(request, model);
        Review review = reviewService.findReview(reviewIdx);

        int deleteResult = reviewService.deleteReview(login.get("loginId"),review);

        return reviewService.returnResult(deleteResult,foodId, model);
    }
}
