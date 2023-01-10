package com.example.foodpreference.dto;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.ItemImg;
import com.example.foodpreference.domain.OrderHistory;
import com.example.foodpreference.domain.OrderItem;

public interface AboutOrder {
  Long getIdx();
  int getItemAmount();
  int getItemPrice();
  Long getItemIdx();
  String getName();
  String getImgPath();
  String getFileName();

  Item getItem();
  ItemImg getItemImg();
  OrderHistory getOrderHistory();
  OrderItem getOrderItem();
}
