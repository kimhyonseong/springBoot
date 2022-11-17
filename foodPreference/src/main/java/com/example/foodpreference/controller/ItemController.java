package com.example.foodpreference.controller;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.dto.ItemDto;
import com.example.foodpreference.dto.ItemImgDto;
import com.example.foodpreference.service.ItemImgService;
import com.example.foodpreference.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class ItemController {
  private final ItemService itemService;
  private final ItemImgService itemImgService;

  @GetMapping({"/item/{idx}","/item"})
  public String itemPage(
          @PathVariable(required = false) Long idx,
          Model model) {
    try {
      if (idx != null) {
        // 불러오기
        Map<String,Object> map = itemService.findItem(idx);
        Map<String,Object> img = itemImgService.showImg((Item) map.get("item"));

        model.addAllAttributes(map);
        model.addAttribute("img",img);
      }
    } catch (RuntimeException e) {
      e.printStackTrace();

      return "error";
    }
    return "item/itemInsert";
  }

  @Transactional
  @PostMapping({"/item/{idx}","/item"})
  public String itemSave(
          @PathVariable(required = false) Long idx,
          @Validated ItemDto itemDto,
          ItemImgDto itemImgDto,
          Model model) {
    try {
      Long saveIdx = itemService.itemSave(itemDto, idx);

      itemImgService.imgSave(itemImgDto,saveIdx);
    } catch (RuntimeException e) {
      log.error("insert error. page : admin/item/"+idx);
      model.addAttribute("errMsg","저장 오류");
      return "item/itemInsert";
    }

    return "main";
  }

  @DeleteMapping("/item/{idx}")
  public void deleteItem(@PathVariable Long idx) {

  }

  @GetMapping("/itemList")
  public String list() {
    return "item/itemList";
  }
}