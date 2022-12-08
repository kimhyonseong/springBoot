package com.example.foodpreference.restController;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ItemRestController {
  private final ItemRepository itemRepository;

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
  public ResponseEntity<?> itemAllList(
          @PageableDefault(size = 20,sort = "regDate", direction = Sort.Direction.DESC) Pageable pageable
  ) {
    List<Item> itemList = null;
    try {
      itemList = itemRepository.findAllByStateIs(pageable,10).getContent();
    } catch (RuntimeException e) {
      return ResponseEntity.badRequest().build();
    }
    return ResponseEntity.ok(itemList);
  }
}
