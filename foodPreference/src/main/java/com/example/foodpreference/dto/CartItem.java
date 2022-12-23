package com.example.foodpreference.dto;

import com.example.foodpreference.domain.Cart;
import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.ItemImg;

//@Setter
//@Getter
//@NoArgsConstructor
public interface CartItem {
//  private Cart cart;
//  private Item item;
//  private ItemImg itemImg;
  Item getItem();
  Cart getCart();
  ItemImg getItemImg();
}
