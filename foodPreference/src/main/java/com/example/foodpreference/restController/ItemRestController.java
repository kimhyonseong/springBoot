package com.example.foodpreference.restController;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.dto.ItemJoinImg;
import com.example.foodpreference.repository.ItemRepository;
import com.example.foodpreference.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ItemRestController {
  private final ItemRepository itemRepository;
  private final ItemService itemService;

  @GetMapping("/itemList/{code}")
  public ResponseEntity<?> itemList(
          @PageableDefault(size = 20,sort = "regDate", direction = Sort.Direction.DESC) Pageable pageable,
          @PathVariable String code) {
    Page<Item> itemList = null;
    try {
      itemList = itemRepository.findAllByCodeAndStateIs(code,10,pageable);
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok(itemList);
  }

  @GetMapping("/itemList/all")
  public ResponseEntity<?> itemAllList(Pageable pageable) {
    Map<String,Object> itemList = itemService.showItemWithImg(pageable);
    return ResponseEntity.ok(itemList);
  }
}
