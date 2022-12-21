package com.example.foodpreference.repository;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.Member;
import com.example.foodpreference.domain.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,Long> {
  Optional<Review> findByIdx(Long idx);
  Optional<Review> findByItemAndMember(Item item, Member member);
  Optional<Review> findByIdxAndMember(Long idx, Member member);
  Optional<Review> findByItemAndMemberId(Item item, String memberIdx);
  //List<Review> findAllByItem(Item item, Pageable pageable); // N+1 문제

  @Query("SELECT r FROM Review AS r JOIN FETCH r.member WHERE r.item.idx = :itemIdx")
  List<Review> findAllFetchJoinMember(@Param("itemIdx") Long itemIdx, Pageable pageable);

  @Query(value = "SELECT SUM(r.score) AS sum,AVG(r.score) AS avg,COUNT(r.score) AS count " +
          "FROM Review AS r WHERE r.item.idx = :itemIdx")
  Map<String,Object> reviewScore(@Param("itemIdx") Long itemIdx);
}
