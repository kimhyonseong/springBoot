package com.example.foodlist.repository;

import com.example.foodlist.domain.Food;
import com.example.foodlist.domain.FoodImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodImgRepository extends JpaRepository<FoodImg,Long> {
    FoodImg findByIdx(Long idx);
    FoodImg findByFood(Food food);
}
