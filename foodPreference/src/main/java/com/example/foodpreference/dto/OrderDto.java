package com.example.foodpreference.dto;

import lombok.*;

import java.util.List;

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
  private List<Long> cartIdxList;
  private String mode;
}
