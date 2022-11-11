package com.example.foodpreference.service;

import com.example.foodpreference.domain.ItemImg;
import com.example.foodpreference.dto.ItemImgDto;
import com.example.foodpreference.repository.ItemImgRepository;
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

  public void imgTmpSave(MultipartFile file) {
    final List<String> ALLOW_TYPE = Arrays.asList("image/jpeg","image/png");
    FileUtil fileUtil = new FileUtil();
    Map<String, Object> fileInfo = fileUtil.fileInfo(file);
    String uploadPath = Paths.get("./images/tmp").toAbsolutePath().toString();

    try {
      if (!ALLOW_TYPE.contains((String) fileInfo.get("contentType"))) {
        System.out.println("업로드 불가능한 파일");
      } else {
        String reName = fileUtil.makeFileName(Objects.requireNonNull(file.getOriginalFilename()));

        File originFile = new File(uploadPath, Objects.requireNonNull(file.getOriginalFilename()));
        File newFile = new File(uploadPath,reName);

        file.transferTo(newFile);
      }
    } catch (Exception e) {
      log.error("img error : "+e.getMessage());
    }
  }
}
