package com.example.foodlist.controller;

import com.example.foodlist.domain.Food;
import com.example.foodlist.domain.FoodImg;
import com.example.foodlist.service.FoodImgService;
import com.example.foodlist.service.FoodService;
import com.example.foodlist.support.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class foodAdminController {
    private final FoodService foodService;
    private final FileUtils fileUtils;
    private final FoodImgService foodImageService;

    @PostMapping(value = {"/food/write","/food/write/{id}"})
    public String foodInsertProc(@Validated Food food,
                                 FoodImg foodImg,
                                 @PathVariable(required = false) Long id,
                                 MultipartFile foodImgFile,
                                 Model model){
        if (id != null) {
            food.setIdx(foodService.showFood(id).getIdx());
            foodImg.setIdx(foodService.showFood(id).getFoodImg().getIdx());
        }
        int result = foodService.put(food);

        model.addAttribute("foodData",food);

        if (result == -1) {
            model.addAttribute("errorMsg","오류가 발생하였습니다.");
            return "admin/foodWrite";
        } else if (result == 0) {
            model.addAttribute("errorMsg","데이터가 비어있습니다.");
            return "admin/foodWrite";
        } else {
            Map<String, Object> fileInfo = fileUtils.fileInfo(foodImgFile);
            foodImg.setSize((Long) fileInfo.get("size"));
            foodImg.setFood(food);
            foodImageService.put(foodImg);
        }

        Map<String, String> redirect = new HashMap<>();
        redirect.put("redirectUrl","/foodList");
        redirect.put("message","정상적으로 저장되었습니다.");
        model.addAttribute("redirect",redirect);

        return "layout/redirect";
    }

    @GetMapping(value = {"/food/write/{id}","/food/write"})
    public String foodUpdatePage(@PathVariable(required = false) Long id, Model model) {

        Food food = null;

        if (id != null) {
            food = foodService.showFood(id);
            System.out.println(food);
        }

        model.addAttribute("foodData", food);
        return "admin/foodWrite";
    }
}
