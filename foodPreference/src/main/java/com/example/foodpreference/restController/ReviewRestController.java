package com.example.foodpreference.restController;

import com.example.foodpreference.domain.Review;
import com.example.foodpreference.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("review")
@RequiredArgsConstructor
public class ReviewRestController {
  private final ReviewService reviewService;

  @GetMapping("/{itemIdx}")
  public Map<String, Object> showReview(@PathVariable Long itemIdx, Pageable pageable) {
    Map<String, Object> result = new HashMap<>();

    try {
      List<Review> reviews = reviewService.showReview(itemIdx, pageable);
      Map<String, Object> totalScore = reviewService.showScore(itemIdx);

      result.put("review",reviews);
      result.put("totalScore",totalScore);

      return result;
    } catch (RuntimeException e) {
      log.error("showReview");
      return null;
    }
  }
}