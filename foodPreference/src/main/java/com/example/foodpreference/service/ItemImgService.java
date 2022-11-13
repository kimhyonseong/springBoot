package com.example.foodpreference.service;

import com.example.foodpreference.domain.Item;
import com.example.foodpreference.domain.ItemImg;
import com.example.foodpreference.dto.ItemImgDto;
import com.example.foodpreference.repository.ItemImgRepository;
import com.example.foodpreference.repository.ItemRepository;
import com.example.foodpreference.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemImgService {
  private final ItemRepository itemRepository;
  private final ItemImgRepository itemImgRepository;

  public Map<String,Object> showImg(Item item) {
    Map<String, Object> map = new HashMap<>();

    ItemImg itemImg = itemImgRepository.findByItem(item);

    if (itemImg != null) {
      map.put("src",itemImg.getImgPath()+itemImg.getImgUrl());
      map.put("imgUrl",itemImg.getImgUrl());
      map.put("size",itemImg.getSize());
      map.put("originName",itemImg.getOriginName());
    } else {
      map = null;
    }

    return map;
  }

  public void imgSave(ItemImgDto imgDto, Long idx) throws RuntimeException {
    Item item;
    ItemImg itemImg;

    try {
      item = itemRepository.findByIdx(idx);
      itemImg = itemImgRepository.findByItem(item);
      String preImg = "";

      if (itemImg == null) {
        itemImg = new ItemImg();
        itemImg.setItem(item);
        preImg = itemImg.getImgUrl();
      }

      itemImg.setImgPath("/images/real/");
      itemImg.setImgUrl(imgDto.getImgUrl());
      itemImg.setOriginName(imgDto.getOriginName());

      itemImgRepository.save(itemImg);

      // 업데이트 시에만 동작
      if (!Objects.equals(preImg, imgDto.getImgUrl())) {
        moveTmpImgToReal(itemImg.getImgUrl());
      }
    } catch (RuntimeException e) {
      log.error("itemImg save error");
      throw new RuntimeException("itemImg save error");
    }
  }

  public Map<String, Object> imgTmpSave(MultipartFile file) {
    final List<String> ALLOW_TYPE = Arrays.asList("image/jpeg", "image/png");
    FileUtil fileUtil = new FileUtil();
    Map<String, Object> fileInfo = fileUtil.fileInfo(file);
    String uploadPath = Paths.get("./images/tmp").toAbsolutePath().toString();

    try {
      if (!ALLOW_TYPE.contains((String) fileInfo.get("contentType"))) {
        log.error("업로드 불가능한 파일");
      } else {
        String reName = fileUtil.makeFileName(Objects.requireNonNull(file.getOriginalFilename()));
        fileInfo.put("newName", reName);
        fileInfo.put("originName", file.getOriginalFilename());

        File newFile = new File(uploadPath, reName);

        file.transferTo(newFile);
        log.info("upload success : " + reName);
      }
    } catch (Exception e) {
      log.error("img error : " + e.getMessage());
    }

    return fileInfo;
  }

  public void moveTmpImgToReal(String fileName) {
    File tmpFile = new File(Paths.get("./images/tmp/").toAbsolutePath().toString(),fileName);
    File moveFile = new File(Paths.get("./images/real/").toAbsolutePath().toString(),fileName);

    if (tmpFile.exists()) {
      if (tmpFile.renameTo(moveFile)) {
        log.info("move to real - "+fileName);
      } else {
        log.error("fail move to real - "+fileName);
      }
    } else {
      log.error("no file - "+fileName);
    }
  }
}
