package com.example.foodpreference.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewRepositoryTest {
  @Autowired
  private ReviewRepository reviewRepository;

  @Nested
  class reviewScore {
    @Test
    @DisplayName("리뷰 1개 이상")
    void moreThanOne() {
      Map<String, Object> map = reviewRepository.reviewScore(1L);
      System.out.println(map.keySet());
      System.out.println(map.get("sum"));
      System.out.println(map.get("avg"));
      System.out.println(map.get("count"));
    }

    @Test
    @DisplayName("리뷰 없을때")
    void haveNoReview() {
      Map<String, Object> map = reviewRepository.reviewScore(3L);
      System.out.println(map.keySet());
      System.out.println(map.get("sum"));
      System.out.println(map.get("avg"));
      System.out.println(map.get("count"));
    }
  }
}