package com.example.foodlist.restController;

import com.example.foodlist.domain.Food;
import com.example.foodlist.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class FoodInfoRestController {
    private final FoodService foodService;

    @GetMapping("/food/info/{id}")
    public ResponseEntity<?> showFoodInfo(@PathVariable Long id) {
        Food food = null;
        if (id != null) {
            // 댓글 쓴거도 같이 보여주기
            food = foodService.showFood(id);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("id : null");
        }
        return ResponseEntity.status(HttpStatus.OK).body(food);
    }
}
