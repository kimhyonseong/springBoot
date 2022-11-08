package com.example.foodpreference.service;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.dto.ItemDto;
import com.example.foodpreference.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemServiceTest {
  @Autowired
  private ItemService itemService;
  @Autowired
  private ItemRepository itemRepository;

  @Test
  void findItemTest() {
    Item item = new Item();
    item.setName("사과");
    item.setQuantity(10);
    item.setPrice(1000);
    item.setCode("과일");
    item.setDescription("강원도 꿀 사과");

    itemRepository.save(item);

    try {
      Map<String, Object> map = itemService.findItem(1L);

      for (Map.Entry<String,Object> entry : map.entrySet()) {
        String key = entry.getKey();
        Object value = entry.getValue();

        System.out.println(key+":"+value);
      }
    } catch (RuntimeException e) {
      System.out.println("1L");
    }

    try {
      itemService.findItem(2L);
    } catch (RuntimeException e) {
      System.out.println("없을때 오류");
    }
  }

  @Test
  void itemSaveTest() {
    ItemDto itemDto = new ItemDto();
    itemDto.setName("사과");
    itemDto.setQuantity(10);
    itemDto.setPrice(1000);
    itemDto.setCode("과일");
    itemDto.setDescription("강원도 꿀 사과");

    try {
      itemService.itemSave(itemDto, null);
      Item item = itemRepository.findByIdx(1L);
      System.out.println(item.toString());
    } catch (RuntimeException e) {
      System.out.println("저장 오류");
    }

    try {
      itemService.itemSave(itemDto, 2L);
    } catch(RuntimeException e) {
      System.out.println("저장 오류 2L");
    }
  }
}