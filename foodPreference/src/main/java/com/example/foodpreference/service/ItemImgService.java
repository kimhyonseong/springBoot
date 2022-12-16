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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ItemImgService {
  private final ItemRepository itemRepository;
  private final ItemImgRepository itemImgRepository;

  public Map<String,Object> showImg(Item item) {
    Map<String, Object> map = new HashMap<>();

    ItemImg itemImg = itemImgRepository.findByItem(item).orElse(null);

    if (itemImg != null) {
      map.put("idx",itemImg.getIdx());
      map.put("src",itemImg.getImgPath()+itemImg.getFileName());
      map.put("fileName",itemImg.getFileName());
      map.put("size",itemImg.getSize());
      map.put("originFileName",itemImg.getOriginFileName());
    } else {
      map = null;
    }

    return map;
  }

  @Transactional
  public void imgSave(ItemImgDto imgDto,Long itemIdx,Long fileIdx) throws RuntimeException {
    if (!(Objects.equals(imgDto.getFileName(), "") || imgDto.getFileName() == null)) {
      try {
        String path = newImgPath();

        Item item = itemRepository.findByIdx(itemIdx);
        ItemImg itemImg = itemImgRepository.findByIdx(fileIdx).orElse(new ItemImg());

        // 이전 파일이름과 현재 파일 이름이 다를때 저장 및 수정
        if (!imgDto.getFileName().equals(itemImg.getFileName())) {
          itemImg.setItem(item);
          itemImg.setImgPath(path);
          itemImg.setFileName(imgDto.getFileName());
          itemImg.setOriginFileName(imgDto.getOriginFileName());

          moveTmpImgToReal(imgDto.getFileName());

          itemImgRepository.save(itemImg);
        }
      } catch (RuntimeException e) {
        log.error("itemImg save error");
        throw new RuntimeException("itemImg save error");
      }
    }
  }

  public Map<String, Object> imgTmpSave(MultipartFile file) {
    final List<String> ALLOW_TYPE = Arrays.asList("image/jpeg", "image/png","image/gif");
    FileUtil fileUtil = new FileUtil();
    Map<String, Object> fileInfo = fileUtil.fileInfo(file);
    String uploadPath = Paths.get("./images/tmp").toAbsolutePath().toString();

    try {
      Path path = Paths.get("./images/tmp").toAbsolutePath();
      Files.createDirectories(path);
    } catch (IOException e) {
      log.error("can't make tmp directory");
      return null;
    }

    try {
      if (!ALLOW_TYPE.contains((String) fileInfo.get("contentType"))) {
        log.error("업로드 불가능한 확장자 : "+fileInfo.get("contentType"));
      } else {
        String reName = fileUtil.makeFileName(Objects.requireNonNull(file.getOriginalFilename()));
        fileInfo.put("fileName", reName);
        fileInfo.put("originFileName", file.getOriginalFilename());

        File newFile = new File(uploadPath, reName);

        file.transferTo(newFile);
        log.info("upload success : " + reName);
      }
    } catch (Exception e) {
      log.error("img error : " + e.getMessage());
    }

    return fileInfo;
  }

  public void moveTmpImgToReal(String fileName) throws RuntimeException {
    String dateDir = newImgPath();

    try {
      Path path = Paths.get("."+dateDir).toAbsolutePath();
      Files.createDirectories(path);

      File tmpFile = new File(Paths.get("./images/tmp/").toAbsolutePath().toString(), fileName);
      File moveFile = new File(Paths.get("."+dateDir).toAbsolutePath().toString(), fileName);

      if (tmpFile.exists()) {
        if (tmpFile.renameTo(moveFile)) {
          log.info("move to real - " + fileName);
        } else {
          log.error("fail move to real - " + fileName);
          throw new RuntimeException("fail move");
        }
      } else {
        log.info("tmpFile - " + tmpFile);
        log.error("no file - " + fileName);
        throw new RuntimeException("no file");
      }
    } catch (IOException e) {
      log.error("fail create Directory");
      throw new RuntimeException();
    } catch (RuntimeException e) {
      log.error("fail move img to real");
      throw new RuntimeException();
    }
  }

  private String newImgPath() {
    LocalDate now = LocalDate.now();
    DateTimeFormatter format = DateTimeFormatter.ofPattern("MM/dd");
    String dateDir = now.format(format)+"/";

    return "/images/real/"+dateDir;
  }
}
