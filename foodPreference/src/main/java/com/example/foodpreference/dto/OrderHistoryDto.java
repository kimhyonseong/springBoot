package com.example.foodpreference.dto;

import java.time.LocalDateTime;

public interface OrderHistoryDto {
  Long getIdx();
  String getAddressee();
  String getMember_address();
  int getOrder_state();
  int getDeliver_cost();
  LocalDateTime getReg_date();
}
