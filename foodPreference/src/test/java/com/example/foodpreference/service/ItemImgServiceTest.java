package com.example.foodpreference.service;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.ItemImg;
import com.example.foodpreference.repository.ItemImgRepository;
import com.example.foodpreference.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemImgServiceTest {
  @Autowired
  ItemRepository itemRepository;

  @Autowired
  ItemImgRepository itemImgRepository;

  @Test
  void imgSave() {
    Item insertItem = new Item();
    insertItem.setName("사과");
    insertItem.setCode("과일");
    insertItem.setPrice(1000);
    insertItem.setQuantity(100);
    insertItem.setDescription("꿀 사과");
    itemRepository.save(insertItem);

    Item item = itemRepository.findByIdx(1L);
    ItemImg itemImg;

    try {
//      itemImg = itemImgRepository.findByItem(item);

//      System.out.println("itemImg"+itemImg);
//      if (itemImg == null) {
        itemImg = new ItemImg();
//      }

//      itemImg.setItem(item);
      itemImg.setImgUrl("tmp.jpg");
      itemImg.setOriginName("originName.jpg");

      Long idx = itemImgRepository.save(itemImg).getIdx();
      ItemImg result = itemImgRepository.findByIdx(idx);
      System.out.println(result);

    } catch (RuntimeException e) {
      System.out.println("itemImg save error");
    }
  }
}