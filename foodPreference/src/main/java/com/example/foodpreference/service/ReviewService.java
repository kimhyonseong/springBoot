package com.example.foodpreference.service;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.Review;
import com.example.foodpreference.repository.ItemRepository;
import com.example.foodpreference.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
  private final ReviewRepository reviewRepository;
  private final ItemRepository itemRepository;

  public List<Review> showReview(Long itemIdx, Pageable pageable) {
    try {
      Item item = itemRepository.findByIdx(itemIdx).orElseThrow(NullPointerException::new);
      List<Review> reviews = reviewRepository.findAllByItem(item,pageable);

      for (Review review : reviews) {
        String tmpDate = review.getRegDate().format(DateTimeFormatter.ISO_DATE);
        //review.setRegDate(tmpDate);
      }

      return reviews;
    } catch (NullPointerException e) {
      log.error("showReview error : item is null");
      return null;
    } catch (RuntimeException e) {
      log.error("showReview error");
      return null;
    }
  }

  public Map<String, Object> showScore(Long itemIdx) {
    Map<String, Object> result = null;

    try {
      result = reviewRepository.reviewScore(itemIdx);
      return result;
    } catch (RuntimeException e) {
      log.error("showScore error");
      return null;
    }
  }
}
