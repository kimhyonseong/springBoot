package com.example.foodpreference.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CartDto {
  private Long itemIdx;
  private int amount;
}
