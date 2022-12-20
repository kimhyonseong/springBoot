package com.example.foodpreference.controller;

import com.example.foodpreference.service.ItemService;
import com.example.foodpreference.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {
  private final ItemService itemService;
  private final ReviewService reviewService;

  @GetMapping("/itemList")
  public String list() {
    return "item/itemList";
  }

  // 잘 나오는지 확인하기
  @GetMapping("/item/{idx}")
  public String itemView(@PathVariable Long idx, Model model, @AuthenticationPrincipal User user) {
    Map<String, Object> reviewMap = reviewService.myReview(user, idx);
    Map<String, Object> map = itemService.findItem(idx);

    if (reviewMap != null) {
      map.put("score",reviewMap.get("score"));
      map.put("comment",reviewMap.get("comment"));
    }
    model.addAllAttributes(map);
    return "item/itemView";
  }

  @GetMapping("/item/buy")
  public String itemBuy() {

    return "item/itemBuy";
  }
}