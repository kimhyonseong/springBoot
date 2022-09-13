package com.example.foodlist.restController;

import com.example.foodlist.domain.Food;
import com.example.foodlist.domain.Review;
import com.example.foodlist.repository.FoodRepository;
import com.example.foodlist.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ReviewRestController {
    private final ReviewRepository reviewRepository;
    private final FoodRepository foodRepository;

    @GetMapping({"/food/review/{id}/{start}","/food/review/{id}"})
    public ResponseEntity<?> showReview(@PathVariable("id") Long foodIdx,
                                        @PathVariable(value = "start", required = false) Integer start) {
        Map<String,Object> body = new HashMap<>();
        Food food = null;
        List<Review> reviews = new ArrayList<>();
        String totalScore = "";

        if (start == null) {
            start = 0;
        }

        try {
            reviews = reviewRepository.findReviewByFoodLimit5(foodIdx,start * 5);
            totalScore = new DecimalFormat("#.00").format(Double.parseDouble(reviewRepository.avgScore(foodIdx)));

            body.put("reviews",reviews);
            body.put("totalScore",totalScore);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
