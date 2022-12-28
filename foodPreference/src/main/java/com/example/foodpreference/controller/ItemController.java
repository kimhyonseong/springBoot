package com.example.foodpreference.controller;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.dto.ItemDto;
import com.example.foodpreference.dto.ItemJoinImg;
import com.example.foodpreference.service.ItemService;
import com.example.foodpreference.service.ReviewService;
import com.example.foodpreference.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
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
  private final ShopService shopService;

  @GetMapping("/itemList")
  public String list() {
    return "item/itemList";
  }

  // 잘 나오는지 확인하기
  @GetMapping("/item/{idx}")
  public String itemView(@PathVariable Long idx, Model model, @AuthenticationPrincipal User user) {
    Map<String, Object> map = itemService.findItem(idx);
    Map<String, Object> reviewMap = reviewService.myReview(user, (Item) map.get("item"));

    if (reviewMap != null) {
      map.put("reviewIdx",reviewMap.get("reviewIdx"));
      map.put("score",reviewMap.get("score"));
      map.put("comment",reviewMap.get("comment"));
    }
    model.addAllAttributes(map);
    return "item/itemView";
  }

  @GetMapping("/item/buy")
  public String itemBuy(Long itemIdx, int amount, Model model, @AuthenticationPrincipal User user) {
    shopService.tmpBuy(itemIdx,amount,user);

    Map<String, Object> map = itemService.showItemWithImg(itemIdx);
    map.put("amount",amount);

    System.out.println(map.get("item"));
    System.out.println(map.get("itemImg"));
    model.addAllAttributes(map);
    return "item/itemBuy";
  }
}