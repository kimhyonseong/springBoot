package com.example.foodpreference.service;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.Member;
import com.example.foodpreference.domain.Review;
import com.example.foodpreference.dto.ReviewDto;
import com.example.foodpreference.repository.ItemRepository;
import com.example.foodpreference.repository.MemberRepository;
import com.example.foodpreference.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {
  private final ReviewRepository reviewRepository;
  private final ItemRepository itemRepository;
  private final MemberRepository memberRepository;

  public List<Review> showReview(Long itemIdx, Pageable pageable) {
    try {
      Item item = itemRepository.findByIdx(itemIdx).orElseThrow(NullPointerException::new);

      return reviewRepository.findAllByItem(item,pageable);
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

  @Transactional
  public int writeReview(Long itemIdx, User user, ReviewDto reviewDto) {
    try {
      Member member = memberRepository.findById(user.getUsername()).orElseThrow(()-> new UsernameNotFoundException("no user"));
      Item item = itemRepository.findByIdx(itemIdx).orElseThrow(()->new IllegalArgumentException("no item"));

      Review review = new Review();
      review.setItem(item);
      review.setMember(member);
      review.setMemberId(user.getUsername());
      review.setScore(reviewDto.getScore());
      review.setComment(reviewDto.getComment());

      reviewRepository.save(review);
      return 200;
    } catch (IllegalArgumentException e) {
      log.error("write review error - no item");
      return 402;
    } catch (UsernameNotFoundException | NullPointerException e) {
      log.error("write review error - not member");
      return 403;
    } catch (RuntimeException e) {
      log.error("write review error");
      return 401;
    }
  }
}
