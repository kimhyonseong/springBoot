package com.example.foodpreference.service;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.dto.ItemDto;
import com.example.foodpreference.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {
  private final ItemRepository itemRepository;

  public void itemSave(ItemDto itemDto) {
    Item item = new Item();

    item.setName(itemDto.getName());
    item.setCode(itemDto.getCode());
    item.setPrice(itemDto.getPrice());
    item.setQuantity(itemDto.getQuantity());

    itemRepository.save(item);
  }

  public void itemModify(ItemDto itemDto, Long idx) {
    Item item = itemRepository.findByIdx(idx);

    item.setName(itemDto.getName());
    item.setCode(itemDto.getCode());
    item.setPrice(itemDto.getPrice());
    item.setQuantity(itemDto.getQuantity());

    itemRepository.save(item);
  }
}
