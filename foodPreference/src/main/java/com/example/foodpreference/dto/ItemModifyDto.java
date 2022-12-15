package com.example.foodpreference.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemModifyDto {
  private String name;
  private String description;
  private String code;
  private int state;
  private int price;
  private int quantity;
  private String fileName;
  private String originFileName;
  private Long fileIdx;
}
