package com.example.foodlist.service;

import com.example.foodlist.domain.Food;
import com.example.foodlist.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodService {
    private final FoodRepository foodRepository;

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
}
