package com.example.foodlist.service;

import com.example.foodlist.domain.Food;
import com.example.foodlist.domain.Member;
import com.example.foodlist.domain.Review;
import com.example.foodlist.repository.MemberRepository;
import com.example.foodlist.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final MemberRepository memberRepository;
    private final ReviewRepository reviewRepository;

    public int putReview(Food food, String memberId, Review review) {
        // 1:정상, 2:로그인문제, 3:음식문제, 4:별점/리뷰 비어있음, -1:에러
        Member member;

        try {
            member = memberRepository.findByMemberId(memberId);

            if (review.getIdx() != null && !Objects.equals(review.getMemberId(), memberId)) {
                return -1;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            return 2;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return -1;
        }

        if (member == null) return 2;
        if (food == null) return 3;

        review.setMemberId(member.getMemberId());
        review.setFood(food);
        review.setMember(member);

        if (Objects.equals(review.getComment(), "") || review.getComment() == null ||
                review.getScore() == 0 || review.getScore() == null) {
            return 4;
        }

        try {
            reviewRepository.save(review);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return -1;
        }

        return 1;
    }

    public int deleteReview(String memberId, Review review) {

        return 5;
    }

    public String returnResult(Integer putResult,Long foodId, Model model) {
        Map<String, String> redirect = new HashMap<>();
        String redirectUrl = "/foodList";
        String message = "작성이 완료되었습니다.";

        if (putResult == -1) {
            message = "오류가 발생하였습니다.";
        } else if (putResult == 2) {
            redirectUrl = "/login";
            message = "다시 로그인 후 이용해주시기 바랍니다.";
        } else if (putResult == 3){
            message = "음식 선택이 되지 않았습니다.";
        } else if (putResult == 4) {
            redirectUrl = "/foodList/"+foodId;
            message = "별점과 코멘트를 달아주세요.";
        } else if (putResult == 5) {
            message = "삭제되었습니다.";
        }

        redirect.put("redirectUrl",redirectUrl);
        redirect.put("message",message);
        model.addAttribute("redirect",redirect);

        return "layout/redirect";
    }

    public Long findReviewId(Long foodIdx,String loginId) {
        Review review = new Review();
        try {
            review = reviewRepository.findByFoodIdxAndMemberIdAndState(foodIdx,loginId,10);
            return review.getIdx();
        } catch (NullPointerException e) {
            return 0L;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    public Long findReviewId(Long reviewIdx) {
        try {
            Review review = reviewRepository.findByIdx(reviewIdx);
            return review.getIdx();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    public Review findReview(Long reviewIdx) {
        try {
            return reviewRepository.findByIdx(reviewIdx);
        } catch (NullPointerException e) {
            return null;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }
}
