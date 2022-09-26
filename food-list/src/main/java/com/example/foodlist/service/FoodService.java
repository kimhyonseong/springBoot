package com.example.foodlist.service;

import com.example.foodlist.domain.Food;
import com.example.foodlist.domain.FoodCategory;
import com.example.foodlist.repository.FoodCategoryRepository;
import com.example.foodlist.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;
    private final FoodCategoryRepository foodCategoryRepository;

    public int put(Food food) {
        if (food != null) {
            try {
                foodRepository.save(food);
                return 1;
            } catch (RuntimeException e) {
                e.printStackTrace();
                return -1;
            }
        }
        return 0;
    }

    public int update(Long id,Food food) {
        if (id != null && food != null) {
            try {
                Food findFood = foodRepository.findById(id).get();
                findFood.setName(food.getName());
                findFood.setFoodCategory(food.getFoodCategory());
                foodRepository.save(findFood);

                return 1;
            } catch (RuntimeException e) {
                e.printStackTrace();
                return -1;
            }
        }
        return 0;
    }

    public List<Food> showAllFoods() {
        try {
            return foodRepository.findAll();
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Food showFood(Long idx) {
        try {
            return foodRepository.findByIdx(idx);
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Food> showFoodByCategory(int categoryCode) {
        List<Food> foodList = new ArrayList<>();

        try {
            FoodCategory foodCategory = foodCategoryRepository.findByCategoryCode(categoryCode);

            if (foodCategory == null) {
                foodList = this.showAllFoods();
            } else {
                foodList = foodRepository.findAllByFoodCategory(foodCategory);
            }
            return foodList;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Food> searchFood(String foodName) {
        List<Food> foodList = new ArrayList<>();

        try {
            foodList = foodRepository.findAllByNameContaining(foodName);
            return foodList;
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        return null;
    }
}
