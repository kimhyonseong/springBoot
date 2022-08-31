package com.example.foodlist.service;

import com.example.foodlist.domain.FoodImg;
import com.example.foodlist.repository.FoodImgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodImgService {
    private final FoodImgRepository foodImgRepository;

    public int put(FoodImg foodImg) {
        try {
            foodImgRepository.save(foodImg);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return -1;
        }
        return 1;
    }

    public Long putReturnIdx(FoodImg foodImg) {
        try {
            foodImgRepository.save(foodImg);
            System.out.println("foodImg idx : "+foodImg.getIdx());
            return foodImg.getIdx();
        } catch (RuntimeException e) {
            e.printStackTrace();
            return -1L;
        }
    }

    public FoodImg putReturnFoodImg(FoodImg foodImg) {
        try {
            foodImgRepository.save(foodImg);
            System.out.println("foodImg idx : "+foodImg.getIdx());
            return foodImg;
        } catch (RuntimeException e) {
            e.printStackTrace();
            return null;
        }
    }
}
