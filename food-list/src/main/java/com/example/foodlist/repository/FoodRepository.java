package com.example.foodlist.repository;

import com.example.foodlist.domain.Food;
import com.example.foodlist.domain.FoodCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food,Long> {
    Food findByIdx(Long idx);

    List<Food> findAllByFoodCategory(FoodCategory category);

    List<Food> findAllByNameContaining(String foodName);
}
