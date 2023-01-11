package com.example.foodpreference.dto;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.ItemImg;
import com.example.foodpreference.domain.OrderHistory;
import com.example.foodpreference.domain.OrderItem;

public interface AboutOrder {
  Item getItem();
  ItemImg getItemImg();
  OrderHistoryDto getOrderHistoryDto();
  OrderItem getOrderItem();
}
