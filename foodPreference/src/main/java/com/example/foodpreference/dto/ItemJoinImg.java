package com.example.foodpreference.dto;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.ItemImg;

import java.util.Optional;

public interface ItemJoinImg {
  Optional<Item> getItem();
  Optional<ItemImg> getItemImg();
}
