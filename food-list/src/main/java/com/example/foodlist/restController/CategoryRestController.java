package com.example.foodlist.restController;

import com.example.foodlist.repository.FoodCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryRestController {
    private final FoodCategoryRepository foodCategoryRepository;

    @GetMapping("/food/category/{categoryId}")
    public ResponseEntity<?> showCategoryFood(@PathVariable int categoryId) {
        foodCategoryRepository.findByCategoryCode(categoryId);

        return ResponseEntity.status(HttpStatus.OK).body(null);
    }
}
