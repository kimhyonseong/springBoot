package com.example.foodpreference.restController;

import com.example.foodpreference.domain.Cart;
import com.example.foodpreference.dto.CartDto;
import com.example.foodpreference.dto.CartItem;
import com.example.foodpreference.dto.OrderDto;
import com.example.foodpreference.service.MemberService;
import com.example.foodpreference.service.ShopService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shopping")
@Slf4j
public class CartRestController {
  private final MemberService memberService;
  private final ShopService shopService;

  @GetMapping("/cart")
  public Map<String, Object> showCart(@AuthenticationPrincipal User user, Pageable pageable) {
    int totalCnt = shopService.countCart(user);
    Map<String,Object> map = new HashMap<>();

    map.put("cart",shopService.showCart(user,pageable));
    map.put("totalCount",totalCnt);
    map.put("currentPage",pageable.getPageNumber());
    map.put("totalPage",(int) Math.ceil((float)totalCnt / pageable.getPageSize()) - 1);

    return map;
  }

  // 테스트 케이스 만들어야함
  @PostMapping("/cart")
  public Map<String,Object> addCart(@RequestBody CartDto cartDto, @AuthenticationPrincipal User user) {
    return makeResult(memberService.isLogin(user),shopService.addCart(cartDto,user));
  }

  @PutMapping("/cart/{idx}")
  public Map<String, Object> updateCart(
          @RequestBody CartDto cartDto,@AuthenticationPrincipal User user,
          @PathVariable Long idx) {
    return makeResult(memberService.isLogin(user),shopService.updateCart(idx,cartDto.getAmount()));
  }

  @DeleteMapping("/cart/{idx}")
  public Map<String, Object> deleteCart(
          @PathVariable Long idx, @AuthenticationPrincipal User user
  ) {
    return makeResult(memberService.isLogin(user),shopService.deleteCart(idx));
  }

  @DeleteMapping("/cart")
  public Map<String, Object> deleteCartAll(@AuthenticationPrincipal User user) {
    return makeResult(memberService.isLogin(user),shopService.deleteAllCart(user));
  }

  @DeleteMapping("/cart/list")
  public Map<String, Object> deleteCartList(@AuthenticationPrincipal User user, @RequestBody List<Long> itemIdxList) {
    System.out.println(itemIdxList);
    return makeResult(memberService.isLogin(user),shopService.deleteCartList(itemIdxList));
  }

  private Map<String, Object> makeResult(boolean login, int service) {
    int code;
    String message;
    Map<String, Object> map = new HashMap<>();
    System.out.println(login);

    if (!login) {
      code = 400;
      message = "로그인이 필요합니다.";
    } else {
      if (service == 200) {
        code = 200;
        message = "정상처리 되었습니다.";
      } else if(service == 501) {
        code = 401;
        message = "아이템 갯수가 재고보다 많습니다.";
      } else {
        code = 500;
        message = "에러가 발생하였습니다.";
      }
    }

    map.put("code",code);
    map.put("message",message);

    return map;
  }

  @PostMapping("/cart/buy")
  public int buyAllItem(@AuthenticationPrincipal User user,
                        @RequestBody(required = false) OrderDto orderDto) {
    try {
      System.out.println(orderDto);
      return shopService.buyAllItem(user);
    } catch (RuntimeException e) {
      log.error("item buy all error - "+e.getMessage());
      return 400;
    }
  }
}
