package com.example.foodpreference.dto;

import com.example.foodpreference.domain.ItemImg;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ItemDto {
  private String name;
  private String description;
  private String code;
  private int state;
  private int price;
  private int quantity;
}
