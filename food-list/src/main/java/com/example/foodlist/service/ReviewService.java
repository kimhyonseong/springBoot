package com.example.foodlist.service;

import com.example.foodlist.domain.Food;
import com.example.foodlist.domain.Member;
import com.example.foodlist.domain.Review;
import com.example.foodlist.repository.MemberRepository;
import com.example.foodlist.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;

    public int putReview(Food food, String memberId, Review review) {
        Member member;

        try {
            member = memberRepository.findByMemberId(memberId);
        } catch (NullPointerException e) {
            e.printStackTrace();
            return 2;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return -1;
        }

        if (food == null) {
            return 0;
        }

        review.setFood(food);
        review.setMember(member);

        try {
            reviewRepository.save(review);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return -1;
        }

        return 1;
    }
}
