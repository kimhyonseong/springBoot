package com.example.foodpreference.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderDto {
  private String addressee;
  private String memberAddress;
  private int orderState;
  private int deliverCost;
  private int amount;
  private int itemPrice;
}
