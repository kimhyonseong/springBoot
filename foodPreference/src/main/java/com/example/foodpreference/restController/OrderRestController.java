package com.example.foodpreference.restController;

import com.example.foodpreference.domain.OrderItem;
import com.example.foodpreference.dto.AboutOrder;
import com.example.foodpreference.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderRestController {
  private final ShopService shopService;

  @GetMapping("/api/order")
  public Page<AboutOrder> showOrderItems(@AuthenticationPrincipal User user, Pageable pageable) {
    try {
      return shopService.showOrderItems(user, pageable);
    } catch (RuntimeException e) {
      return null;
    }
  }
}
