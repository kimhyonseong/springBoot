package com.example.foodpreference.dto;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.Member;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class CartDto {
  private Long itemIdx;
  private int amount;
}
