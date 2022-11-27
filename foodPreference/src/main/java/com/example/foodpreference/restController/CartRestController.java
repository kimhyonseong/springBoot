package com.example.foodpreference.restController;

import com.example.foodpreference.dto.CartDto;
import com.example.foodpreference.service.MemberService;
import com.example.foodpreference.service.ShopService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CartRestController {
  private final MemberService memberService;
  private final ShopService shopService;

  @Transactional
  @PostMapping("/addCart")
  public Map<String,Object> addCart(@RequestBody CartDto cartDto, @AuthenticationPrincipal User user) {
    int code;
    String message;
    Map<String, Object> map = new HashMap<>();

    if(memberService.isLogin(user)) {
      // 카트 생성 및 아이템 넣기
      if (shopService.addCart(cartDto,user)) {
        code = 200;
        message = "정상처리 되었습니다.";
      } else {
        code = 500;
        message = "에러가 발생하였습니다.";
      }
    } else {
      code = 400;
      message = "로그인이 필요합니다.";
    }

    map.put("code",code);
    map.put("message",message);

    return map;
  }
}
