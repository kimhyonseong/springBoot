package com.example.foodlist.restController;

import com.example.foodlist.domain.Food;
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
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class FoodInfoRestController {
    private final FoodService foodService;
    private final ReviewService reviewService;

    @GetMapping("/food/info/{foodIdx}")
    public ResponseEntity<?> showFoodInfo(
            HttpServletRequest request,
            @PathVariable Long foodIdx) {
        Food food = null;
        String loginId = "";
        Cookie[] cookies = request.getCookies();

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
            // 댓글 쓴거도 같이 보여주기
            reviewService.findReviewId(foodIdx,loginId);
            food = foodService.showFood(foodIdx);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id : null");
        }
        return ResponseEntity.status(HttpStatus.OK).body(food);
    }
}
