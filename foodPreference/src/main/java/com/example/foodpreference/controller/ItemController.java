package com.example.foodpreference.controller;

import com.example.foodpreference.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {
  private final ItemService itemService;

  @GetMapping("/itemList")
  public String list() {
    return "item/itemList";
  }

  @GetMapping("/item/{idx}")
  public String itemView(@PathVariable Long idx, Model model) {
    Map<String, Object> map = itemService.findItem(idx);
    model.addAllAttributes(map);
    return "item/itemView";
  }

  @GetMapping("/item/buy")
  public String itemBuy() {

    return "item/itemBuy";
  }
}