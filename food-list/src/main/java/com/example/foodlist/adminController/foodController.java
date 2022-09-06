package com.example.foodlist.adminController;

import com.example.foodlist.domain.Food;
import com.example.foodlist.domain.FoodImg;
import com.example.foodlist.domain.Member;
import com.example.foodlist.domain.Review;
import com.example.foodlist.service.FoodImgService;
import com.example.foodlist.service.FoodService;
import com.example.foodlist.service.ReviewService;
import com.example.foodlist.support.FileUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class foodController {
    private final FoodService foodService;
    private final FileUtils fileUtils;
    private final FoodImgService foodImageService;
    private final ReviewService reviewService;

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

    @GetMapping(value = "food/write/{id}")
    public String foodUpdatePage(@PathVariable Long id, @Validated Food food,Model model) {

        model.addAttribute("foodData",food);
        return "common/foodList";
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
    public String foodListPage(Model model) {
        List<Food> foodList = foodService.showAllFoods();
        model.addAttribute("foodList",foodList);
        return "common/foodList";
    }

    @GetMapping("foodList/{id}")
    public String foodListPage(Model model, @PathVariable String id) {
        List<Food> foodList = foodService.showAllFoods();
        model.addAttribute("foodList",foodList);
        model.addAttribute("foodId",id);
        return "common/foodList";
    }

    @PostMapping("/food/review")
    public String foodReview(HttpServletRequest request, Model model,
                             Review review, @RequestParam("foodId") Long foodId) {
        Food food = foodService.showFood(foodId);
        Cookie[] cookies = request.getCookies();
        String loginId = "";

        for(Cookie c : cookies) {
            if (Objects.equals(c.getName(), "loginId")) {
                loginId = c.getValue();
            }
        }

        review.setMemberId(loginId);
        int putResult = reviewService.putReview(food,loginId,review);

        return reviewService.returnResult(putResult,foodId, model);
    }
}
