package com.example.foodlist.adminController;

import com.example.foodlist.domain.Food;
import com.example.foodlist.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class foodController {
    private FoodService foodService;

    @GetMapping(value = "food/write")
    public String foodInsertPage() {
        return "admin/foodWrite";
    }

    @PostMapping(value = "food/write")
    public String foodInsertProc(@Validated Food food, Model model){
        int result = foodService.put(food);

        model.addAttribute("foodData",food);

        if (result == -1) {
            model.addAttribute("errorMsg","오류가 발생하였습니다.");
            return "admin/foodWrite";
        } else if (result == 0) {
            model.addAttribute("errorMsg","데이터가 비어있습니다.");
        }

        return "admin/foodList";
    }

    @PostMapping(value = "food/write/{id}")
    public String foodUpdateProc(@PathVariable Long id, @Validated Food food,Model model) {
        int result = foodService.update(id,food);

        model.addAttribute("foodData",food);

        if (result == -1) {
            model.addAttribute("errorMsg","오류가 발생하였습니다.");
            return "admin/foodWrite";
        } else if (result == 0) {
            model.addAttribute("errorMsg","데이터가 비어있습니다.");
        }

        return "admin/foodList";
    }
}
