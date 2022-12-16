package com.example.foodpreference.dto;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ItemModifyDto extends ItemDto {
  private String fileName;
  private String originFileName;
  private Long fileIdx;
}
