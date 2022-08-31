package com.example.foodlist.adminController;

import com.example.foodlist.domain.Food;
import com.example.foodlist.domain.FoodImg;
import com.example.foodlist.service.FoodImgService;
import com.example.foodlist.service.FoodService;
import com.example.foodlist.support.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class foodController {
    private final FoodService foodService;
    private final FileUtils fileUtils;
    private final FoodImgService foodImageService;

    @GetMapping(value = "food/write")
    public String foodInsertPage() {
        return "admin/foodWrite";
    }

    @PostMapping(value = "food/write")
    public String foodInsertProc(@Validated Food food,
                                 FoodImg foodImg,
                                 MultipartFile foodImgFile,
                                 Model model){
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

        return "redirect:/foodList";
    }

    @GetMapping("foodList")
    public String foodListPage() {
        return "admin/foodList";
    }
}
