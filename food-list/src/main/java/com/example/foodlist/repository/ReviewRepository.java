package com.example.foodlist.repository;

import com.example.foodlist.domain.Food;
import com.example.foodlist.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review,Long> {
    Review findByIdx(Long reviewIdx);
    List<Review> findAllByFood(Food food);
    Review findByFoodIdxAndMemberId(Long foodIdx,String memberId);
    Review findByFoodIdxAndMemberIdAndState(Long foodIdx,String memberId,Integer state);

    @Query(value = "select avg(score) over(partition by food_idx) result from review " +
            "where food_idx = :foodIdx and state = 10 limit 1", nativeQuery = true)
    String avgScore(@Param("foodIdx") Long foodIdx);

    @Query(nativeQuery = true,
            value = "select a.* from review a " +
                    "join member b on a.member_idx = b.idx " +
                    "where a.food_idx = :foodIdx and a.state = 10 and b.state = 10 limit :start,:end")
    List<Review> findReviewByFoodLimit(@Param("foodIdx")Long foodIdx,
                                        @Param("start")Integer start,
                                        @Param("end")Integer end);

    @Query(nativeQuery = true,
            value = "select count(*) as all_count from review a " +
                    "join member b on a.member_idx = b.idx " +
                    "where a.food_idx = :foodIdx and a.state = 10 and b.state = 10")
    Integer foodReviewCount(@Param("foodIdx")Long foodIdx);
}
