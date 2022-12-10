package com.example.foodpreference.dto;

import com.example.foodpreference.domain.ItemImg;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
  private String name;
  private String description;
  private String code;
  private int state;
  private int price;
  private int quantity;
}
