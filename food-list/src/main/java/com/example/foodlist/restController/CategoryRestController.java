package com.example.foodlist.restController;

import com.example.foodlist.domain.Food;
import com.example.foodlist.repository.FoodCategoryRepository;
import com.example.foodlist.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryRestController {
    private final FoodService foodService;

    @GetMapping("/food/category/{categoryId}")
    public Object showCategoryFood(@PathVariable Integer categoryId) {
        return foodService.showFoodByCategory(categoryId);
    }
}
