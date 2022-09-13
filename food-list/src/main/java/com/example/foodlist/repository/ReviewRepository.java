package com.example.foodlist.repository;

import com.example.foodlist.domain.Food;
import com.example.foodlist.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    List<Review> findAllByFood(Food food);
    List<Review> findTop5ByFood(Food food);
    Review findByFoodIdxAndMemberId(Long foodIdx,String memberId);

    @Query(value = "select avg(score) over(partition by food_idx) result from review " +
            "where food_idx = :foodIdx limit 1", nativeQuery = true)
    String avgScore(@Param("foodIdx") Long foodIdx);

    @Query(nativeQuery = true,
            value = "select * from review where food_idx = :foodIdx limit :start,5")
    List<Review> findReviewByFoodLimit5(@Param("foodIdx")Long foodIdx,
                                        @Param("start")Integer start);
}
