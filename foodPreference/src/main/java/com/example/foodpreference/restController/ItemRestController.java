package com.example.foodpreference.restController;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.dto.OrderDto;
import com.example.foodpreference.repository.ItemRepository;
import com.example.foodpreference.service.ItemService;
import com.example.foodpreference.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ItemRestController {
  private final ItemRepository itemRepository;
  private final ItemService itemService;
  private final ShopService shopService;

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

  @PostMapping("/item/buy/{itemIdx}")
  public int buyItem(@RequestBody OrderDto orderDto, @PathVariable Long itemIdx, @AuthenticationPrincipal User user) {
    try {
      return shopService.buyOneItem(orderDto,itemIdx,user);
    } catch (RuntimeException e) {
      log.error("item buy error");
      return 400;
    }
  }
}
