package com.example.foodpreference.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ReviewDto {
  private int score;
  private String comment;
}
