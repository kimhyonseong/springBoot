package com.example.foodpreference.restController;

import com.example.foodpreference.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberRestController {
  private final MemberService memberService;

  @GetMapping("/join/{id}/exist")
  public ResponseEntity<Boolean> checkId(@PathVariable String id) {
    return ResponseEntity.ok(memberService.checkMemberIdDuplication(id));
  }
}
