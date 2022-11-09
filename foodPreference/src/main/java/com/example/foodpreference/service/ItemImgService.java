package com.example.foodpreference.service;

import com.example.foodpreference.domain.ItemImg;
import com.example.foodpreference.dto.ItemImgDto;
import com.example.foodpreference.repository.ItemImgRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemImgService {
  private final ItemImgRepository itemImgRepository;

  // 이미지 여러개 들어올 예정
  public void ImgSave(MultipartFile file, Long idx) throws RuntimeException{
    try {
      ItemImg itemImg = null;
      if (idx != null) {
        itemImg = itemImgRepository.findByIdx(idx);
      } else {
        itemImg = new ItemImg();
      }
      itemImgRepository.save(itemImg);
    } catch (RuntimeException e) {
      log.error("itemImg save error");
      throw new RuntimeException("itemImg save error");
    }
  }
}
