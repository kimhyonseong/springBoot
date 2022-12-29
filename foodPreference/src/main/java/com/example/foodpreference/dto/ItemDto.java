package com.example.foodpreference.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ItemDto {
  private String name;
  private String description;
  private String code;
  private int state;
  private int price;
  private int quantity;
}
