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

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReviewRestController {
    private final ReviewRepository reviewRepository;
    private final FoodRepository foodRepository;

    @GetMapping("/food/review/{id}")
    public ResponseEntity<?> showReview(@PathVariable("id") Long foodIdx) {
        Food food = null;
        List<Review> reviews = new ArrayList<>();

        try {
            food = foodRepository.findByIdx(foodIdx);
            reviews = reviewRepository.findAllByFood(food);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(null);
        }

        return ResponseEntity.status(HttpStatus.OK).body(reviews);
    }
}
