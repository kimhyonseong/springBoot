package com.example.foodlist.service;

import com.example.foodlist.domain.Food;
import com.example.foodlist.domain.FoodImg;
import com.example.foodlist.repository.FoodImgRepository;
import com.example.foodlist.repository.FoodRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FoodServiceTest {
    @Autowired
    private FoodService foodService;
    @Autowired
    private FoodRepository foodRepository;
    @Autowired
    private FoodImgService foodImgService;
    @Autowired
    private FoodImgRepository foodImgRepository;

    @Test
    void showFoodInfo() {
        Food insertFood = new Food();
        insertFood.setName("김치");
        insertFood.setCountry("한국");
        insertFood.setCountryCode(1);
        insertFood.setDisplay(10);

        foodService.put(insertFood);

        FoodImg insertFoodImg = new FoodImg();
        insertFoodImg.setName(insertFood.getName());
        insertFoodImg.setFood(insertFood);
        insertFoodImg.setSize(100L);
        insertFoodImg.setImgUrl("localhost:8080/images/test.jpg");;

        foodImgService.put(insertFoodImg);

        Food food = foodRepository.findByIdx(1L);
        System.out.println("Food : "+food);

        FoodImg foodImg = foodImgRepository.findByFood(foodRepository.findByIdx(1L));
        System.out.println("foodImg : "+foodImg);

        System.out.println("Food List : "+foodRepository.findAll());
    }
}