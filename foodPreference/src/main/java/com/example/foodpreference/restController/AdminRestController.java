package com.example.foodpreference.restController;

import com.example.foodpreference.dto.ItemImgDto;
import com.example.foodpreference.dto.ItemModifyDto;
import com.example.foodpreference.service.ItemImgService;
import com.example.foodpreference.service.ItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("admin")
public class AdminRestController {
  private final ItemService itemService;
  private final ItemImgService itemImgService;

  @Transactional
  @DeleteMapping("/item/{idx}")
  public Boolean deleteItem(@PathVariable Long idx, @AuthenticationPrincipal User user, Model model) {
    try {
      if (!itemService.itemDelete(user,idx)) {
        throw new RuntimeException("error");
      }
      return true;
    } catch (RuntimeException e) {
      log.error("fail delete item");
      return false;
    }
  }

  @Transactional
  @PatchMapping({"/item/{idx}"})
  public Boolean itemModify(
          @PathVariable Long idx,
          @RequestBody(required = false) ItemModifyDto itemModifyDto,
          Model model) {
    try {
      ItemImgDto itemImgDto = new ItemImgDto(itemModifyDto.getFileName(),itemModifyDto.getFileName());
      Long fileIdx = itemModifyDto.getFileIdx();

      Long itemIdx = itemService.itemModify(itemModifyDto, idx);
      itemImgService.imgSave(itemImgDto, itemIdx, fileIdx);
    } catch (RuntimeException e) {
      log.error("insert error. page : admin/item/"+idx);
      model.addAttribute("errMsg","저장 오류");
      return false;
    }

    return true;
  }
}
