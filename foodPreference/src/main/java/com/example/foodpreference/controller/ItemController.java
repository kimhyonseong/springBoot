package com.example.foodpreference.controller;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.dto.ItemDto;
import com.example.foodpreference.repository.ItemRepository;
import com.example.foodpreference.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {
  private final ItemService itemService;
  private final ItemRepository itemRepository;

  @GetMapping({"admin/item/{idx}","admin/item"})
  public String foodPage(
          @PathVariable(required = false) Long idx,
          Model model) {
    try {
      if (idx != null) {
        // 불러오기
        Item item = itemRepository.findByIdx(idx);
        Map<String,Object> map = new HashMap<>();
        map.put("name",item.getName());

        model.addAllAttributes(map);
      }
    } catch (RuntimeException e) {
      e.printStackTrace();
    }
    return "item/itemInsert";
  }

  @PostMapping({"admin/item/{idx}","admin/item"})
  public String foodSave(
          @PathVariable(required = false) Long idx,
          @Validated ItemDto itemDto) {
    try {
      itemService.itemSave(itemDto, idx);
    } catch (Exception e) {
      log.error("insert error. page : admin/item/"+idx);
      return "item/itemInsert";
    }

    return "item/itemInsert";
  }
}