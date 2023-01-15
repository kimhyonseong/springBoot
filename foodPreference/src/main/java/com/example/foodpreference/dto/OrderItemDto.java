package com.example.foodpreference.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemDto {
  private Long idx;
  private Long itemIdx;
  private int amount;
  private int price;
  private String path;
  private String imgName;
  private String name;
}
