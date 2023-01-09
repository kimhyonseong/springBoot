package com.example.foodpreference.dto;

import com.example.foodpreference.domain.Cart;
import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.ItemImg;

public interface CartItem {
  Item getItem();
  Cart getCart();
  ItemImg getItemImg();
}
