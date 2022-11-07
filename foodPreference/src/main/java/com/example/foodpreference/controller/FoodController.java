package com.example.foodpreference.controller;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.dto.ItemDto;
import com.example.foodpreference.repository.ItemRepository;
import com.example.foodpreference.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class FoodController {
  private final ItemService itemService;
  private final ItemRepository itemRepository;

  @GetMapping({"admin/food/{idx}","admin/food"})
  public String foodPage(
          @PathVariable(required = false) Long idx,
          Model model) {
    if (idx != null) {
      // 불러오기
      Item item = itemRepository.findByIdx(idx);
      Map<String,Object> map = new HashMap<>();
      map.put("name",item.getName());

      model.addAllAttributes(map);
    }
    return "food/foodInsert";
  }

  @PostMapping({"admin/food/{idx}","admin/food"})
  public String foodSave(
          @PathVariable(required = false) Long idx,
          @Validated ItemDto itemDto) {
    if (idx == null) {
      // 새로저장
      itemService.itemSave(itemDto);
    } else {
      // 수정
      itemService.itemModify(itemDto, idx);
    }
    return "food/foodInsert";
  }
}