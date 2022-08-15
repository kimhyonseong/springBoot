package com.example.foodlist.repository;

import com.example.foodlist.domain.FoodReviewInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodReviewInfoRepository extends JpaRepository<FoodReviewInfo,Long> {
}
