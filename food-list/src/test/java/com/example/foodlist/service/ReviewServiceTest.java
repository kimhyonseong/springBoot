package com.example.foodlist.service;

import com.example.foodlist.domain.Food;
import com.example.foodlist.domain.Member;
import com.example.foodlist.domain.Review;
import com.example.foodlist.repository.FoodRepository;
import com.example.foodlist.repository.MemberRepository;
import com.example.foodlist.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ReviewServiceTest {
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private FoodService foodService;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewService reviewService;

    @Test
    void purePut() {
        Member member = new Member();
        member.setMemberId("lss1545");
        member.setName("이순신");
        member.setMemberPw("1234");
        member.setState(10);
        memberRepository.save(member);

        Food insertFood = new Food();
        insertFood.setName("빵");
        insertFood.setDisplay(10);
        insertFood.setState(10);
        insertFood.setCountry("배고픔국");
        insertFood.setCountryCode(1);
        foodRepository.save(insertFood);

        String memberId = "lss1545";
        Food food = foodService.showFood(1L);

        try {
            System.out.println("memberId = "+memberId);
            member = memberRepository.findByMemberId(memberId);
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("Null point Exception");
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.out.println("Runtime error1");
        }

        if (food == null) {
            System.out.println("food : null");
        }

        System.out.println("food : "+food);
        System.out.println("member : "+member);
    }

    @Test
    void putReviewTest() {
        Member member = new Member();
        member.setMemberId("lss1545");
        member.setName("이순신");
        member.setMemberPw("1234");
        member.setState(10);
        memberRepository.save(member);
        memberRepository.flush();

        Food insertFood = new Food();
        insertFood.setName("빵");
        insertFood.setDisplay(10);
        insertFood.setState(10);
        insertFood.setCountry("배고픔국");
        insertFood.setCountryCode(1);
        foodRepository.save(insertFood);

        String memberId = "lss1545";
        Food food = foodService.showFood(1L);

        Review review = new Review();
        review.setComment("굳뜨");
        review.setScore(5);

        int result = reviewService.putReview(food,memberId,review);
        System.out.println("result : "+result);
    }
}