package com.example.foodpreference.repository;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.Review;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface ReviewRepository extends JpaRepository<Review,Long> {
  List<Review> findAllByItem(Item item, Pageable pageable);

  @Query(value = "SELECT SUM(r.score) AS sum,AVG(r.score) AS avg,COUNT(r.score) AS count " +
          "FROM Review AS r WHERE r.item.idx = :itemIdx")
  Map<String,Object> reviewScore(@Param("itemIdx") Long itemIdx);
}
