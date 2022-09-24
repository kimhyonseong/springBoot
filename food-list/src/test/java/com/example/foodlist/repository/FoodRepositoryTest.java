package com.example.foodlist.repository;

import com.example.foodlist.domain.Food;
import com.example.foodlist.domain.FoodCategory;
import com.example.foodlist.service.FoodService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FoodRepositoryTest {
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private FoodCategoryRepository foodCategoryRepository;
    @Autowired
    private FoodService foodService;

    @Test
    void selectByCategory() {
        List<Food> foodList = new ArrayList<>();
        FoodCategory foodCategory = foodCategoryRepository.findByCategoryCode(6);

        if (foodCategory == null) {
            foodList = foodService.showAllFoods();
        } else {
            foodList = foodRepository.findAllByFoodCategory(foodCategory);
        }

        System.out.println("foodCategory = "+foodCategory);
        System.out.println("foodList = "+foodList);
    }
}