package com.example.foodlist.restController;

import com.example.foodlist.domain.Food;
import com.example.foodlist.domain.Review;
import com.example.foodlist.repository.ReviewRepository;
import com.example.foodlist.service.FoodService;
import com.example.foodlist.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class FoodInfoRestController {
    private final FoodService foodService;
    private final ReviewRepository reviewRepository;
    private final ReviewService reviewService;

    @GetMapping("/food/info/{foodIdx}")
    public ResponseEntity<?> showFoodInfo(
            HttpServletRequest request,
            @PathVariable Long foodIdx) {
        Map<String,Object> body = new HashMap<>();
        Food food = null;
        Review myReview = null;
        String loginId = "";
        Cookie[] cookies = request.getCookies();
        String totalScore = "";
        int totalCount = 0;

        try {
            for (Cookie cookie : cookies) {
                if (Objects.equals(cookie.getName(), "loginId")) {
                    loginId = cookie.getValue();
                }
            }
        } catch (NullPointerException e) {
            System.out.println("로그인 하지 않은 아이디");
        }

        if (foodIdx != null) {
            myReview = reviewRepository.findByFoodIdxAndMemberIdAndState(foodIdx,loginId,10);
            System.out.println("my review");
            totalScore = new DecimalFormat("#.00").format(Double.parseDouble(reviewRepository.avgScore(foodIdx)));
            System.out.println("review score");
            totalCount = reviewRepository.foodReviewCount(foodIdx);
            System.out.println("review count");
            food = foodService.showFood(foodIdx);

            body.put("food",food);
            body.put("myReview",myReview);
            body.put("totalScore",totalScore);
            body.put("totalCount",totalCount);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id : null");
        }
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
