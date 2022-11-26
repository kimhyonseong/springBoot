package com.example.foodpreference.dto;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartDto {
  private String memberId;
  private Long itemIdx;
  private int amount;
}
