package com.example.foodpreference.service;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.dto.ItemDto;
import com.example.foodpreference.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemService {
  private final ItemRepository itemRepository;

  public Map<String,Object> findItem(Long idx) throws RuntimeException{
    Map<String, Object> map = new HashMap<>();

    try {
      Item item = itemRepository.findByIdx(idx);
      map.put("item",item);
      map.put("name",item.getName());
      map.put("code",item.getCode());
      map.put("description",item.getDescription());
      map.put("price",item.getPrice());
      map.put("quantity",item.getQuantity());

    } catch (RuntimeException e) {
      log.error("존재하지 않는 idx - "+idx);
      throw new RuntimeException("not exist item idx");
    }

    return map;
  }

  public Long itemSave(ItemDto itemDto, Long idx) throws RuntimeException {
    try {
      Item item = null;

      if (idx == null) {
        item = new Item();
      } else {
        item = itemRepository.findByIdx(idx);
      }

      item.setName(itemDto.getName());
      item.setDescription(itemDto.getDescription());
      item.setCode(itemDto.getCode());
      item.setPrice(itemDto.getPrice());
      item.setQuantity(itemDto.getQuantity());

      return itemRepository.save(item).getIdx();
    } catch (RuntimeException e) {
      log.error("save error");
      throw new RuntimeException("item save error");
    }
  }
}
