package com.example.foodpreference.dto;

public interface OrderItemImpl {
  Long getIdx();
  int getAmount();
  int getPrice();
  Long getItemIdx();
  String getPath();
  String getImgName();
  String getName();
}
