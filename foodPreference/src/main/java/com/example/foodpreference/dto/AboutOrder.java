package com.example.foodpreference.dto;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.ItemImg;
import com.example.foodpreference.domain.OrderHistory;
import com.example.foodpreference.domain.OrderItem;

public interface AboutOrder {
<<<<<<< HEAD
  Item getItem();
  ItemImg getItemImg();
  OrderHistoryDto getOrderHistoryDto();
=======
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
>>>>>>> fea91f8504707c804b1f9a13c072962dac5e71c7
  OrderItem getOrderItem();
}
