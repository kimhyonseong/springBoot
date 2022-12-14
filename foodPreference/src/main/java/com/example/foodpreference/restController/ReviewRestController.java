package com.example.foodpreference.restController;

import com.example.foodpreference.domain.Review;
import com.example.foodpreference.dto.ReviewDto;
import com.example.foodpreference.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("review")
@RequiredArgsConstructor
public class ReviewRestController {
  private final ReviewService reviewService;

  @GetMapping("/item/{itemIdx}")
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

  @PostMapping
  public int writeReview(@AuthenticationPrincipal User user, @RequestBody ReviewDto reviewDto) {
    try {
      return reviewService.writeReview(user,reviewDto);
    } catch (RuntimeException e) {
      log.error("review write error");
      return 400;
    }
  }

  @PatchMapping("/{reviewId}")
  public int modifyReview(@PathVariable Long reviewId, @AuthenticationPrincipal User user, @RequestBody ReviewDto reviewDto) {
    try {
      return reviewService.modifyReview(reviewId, user, reviewDto);
    } catch (RuntimeException e) {
      log.error("modify write error");
      return 400;
    }
  }

  @DeleteMapping("/{reviewId}")
  public int deleteReview(@PathVariable Long reviewId, @AuthenticationPrincipal User user) {
    try {
      return reviewService.deleteReview(reviewId, user);
    } catch (RuntimeException e) {
      log.error("delete review error");
      return 400;
    }
  }
}