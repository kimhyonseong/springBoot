package com.example.foodpreference.dto;

import com.example.foodpreference.domain.BaseEntity;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class OrderHistoryDto extends BaseEntity {
  private Long idx;
  private String addressee;
  private String memberAddress;
  private int orderState;
  private int deliverCost;
  //private Map<String, Object> lists = new HashMap<>();
}
