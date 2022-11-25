package com.example.foodpreference.restController;

import com.example.foodpreference.domain.Member;
import com.example.foodpreference.repository.MemberRepository;
import com.example.foodpreference.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CartRestController {
  private final MemberService memberService;

  @PostMapping("/addCart")
  public Map<String,Object> addCart(@AuthenticationPrincipal User user) {
    int code;
    String message;
    Map<String, Object> map = new HashMap<>();

    if(memberService.isLogin(user)) {
      // 카트 생성 및 아이템 넣기
      code = 200;
      message = "정상처리 되었습니다.";
    } else {
      code = 400;
      message = "로그인이 필요합니다.";
    }

    map.put("code",code);
    map.put("message",message);

    return map;
  }
}
