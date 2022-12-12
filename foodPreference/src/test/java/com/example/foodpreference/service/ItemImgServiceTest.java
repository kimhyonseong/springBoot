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
    try {
      ItemImg itemImg = new ItemImg();
      itemImg.setFileName("tmp.jpg");
      itemImg.setOriginFileName("originName.jpg");
      Long insertImgIdx = itemImgRepository.save(itemImg).getIdx();
      System.out.println(insertImgIdx);
      ItemImg resultImg = itemImgRepository.findByIdx(insertImgIdx).orElse(null);
      System.out.println(resultImg);

      Item item = new Item();
      item.setName("사과");
      item.setCode("과일");
      item.setPrice(1000);
      item.setQuantity(100);
      item.setDescription("꿀 사과");
      Long insertIdx = itemRepository.save(item).getIdx();

      Item findItem = itemRepository.findByIdx(insertIdx);

      System.out.println(findItem);

    } catch (RuntimeException e) {
      e.printStackTrace();
      System.out.println("itemImg save error");
    }
  }
}