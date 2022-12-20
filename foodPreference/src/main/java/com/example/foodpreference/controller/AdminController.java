package com.example.foodpreference.controller;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.ItemImg;
import com.example.foodpreference.dto.ItemDto;
import com.example.foodpreference.dto.ItemImgDto;
import com.example.foodpreference.dto.ItemJoinImg;
import com.example.foodpreference.dto.ItemModifyDto;
import com.example.foodpreference.service.AdminService;
import com.example.foodpreference.service.ItemImgService;
import com.example.foodpreference.service.ItemService;
import com.example.foodpreference.utils.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("admin")
public class AdminController {
  private final ItemService itemService;
  private final ItemImgService itemImgService;
  private final AdminService adminService;

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
    return "admin/itemInsert";
  }

  @Transactional
  @PostMapping({"/item"})
  public String itemSave(
          @Validated ItemDto itemDto,
          ItemImgDto itemImgDto,
          @AuthenticationPrincipal User user,
          Model model) {
    Map<String, String> result = new HashMap<>();
    try {
      System.out.println(itemDto.getName());  // null..?
      Long itemIdx = itemService.itemSave(itemDto,user);
      itemImgService.imgSave(itemImgDto,itemIdx,null);

      result.put("url","/admin/myItem");
      result.put("message","정상적으로 처리되었습니다.");

      model.addAllAttributes(result);

    } catch (RuntimeException e) {
      log.error("insert error. page : admin/item/");
      model.addAttribute("errMsg","저장 오류");
      return "item/itemInsert";
    }

    return "common/redirect";
  }

  @GetMapping("/myItem")
  public String myItem(@AuthenticationPrincipal User user, Pageable pageable, Model model) {
    Map<String, Object> map = new HashMap<>();
    List<ItemJoinImg> itemList = adminService.findAdminItem(user,pageable);
    Page page = new Page(adminService.countAdminItem(user), pageable.getPageNumber()+1);
    page.setLink("/admin/myItem");

    map.put("itemList",itemList);
    map.put("paging",page);

    model.addAllAttributes(map);

    return "admin/myItem";
  }
}
