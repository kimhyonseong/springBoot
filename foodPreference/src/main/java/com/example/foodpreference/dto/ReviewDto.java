package com.example.foodpreference.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReviewDto {
  private Long idx;
  private int score;
  private String comment;
  private Long itemIdx;
}
