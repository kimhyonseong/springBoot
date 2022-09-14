package com.example.foodlist.repository;

import com.example.foodlist.domain.Food;
import com.example.foodlist.domain.Member;
import com.example.foodlist.domain.Review;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewRepositoryTest {
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    FoodRepository foodRepository;

    @Test
    void avgTest() {
        Food food = foodRepository.findByIdx(1L);
        Member member = new Member();
        Review review = new Review();

        member.setMemberId("lss1234");
        member.setMemberPw("1234");
        member.setName("이순신2");
        memberRepository.save(member);

        review.setMemberId("lss1234");
        review.setScore(4);
        review.setComment("굳");
        review.setFood(food);
        review.setMember(member);
        reviewRepository.save(review);

        String avg = reviewRepository.avgScore(1L);
        double result = Double.parseDouble(avg);
        DecimalFormat df = new DecimalFormat("###.00");

        System.out.println(new DecimalFormat("###.00").format(result));
    }

    @Test
    void updateTest() {
        Food food = foodRepository.findByIdx(1L);
        Member member = memberRepository.findByMemberId("lss1545");
        Review review = new Review();

        review.setScore(4);
        review.setIdx(1L);
        review.setMember(member);
        review.setFood(food);

        reviewRepository.save(review);
    }
}