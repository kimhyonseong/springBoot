package com.example.foodpreference.restController;

import com.example.foodpreference.service.ItemImgService;
import com.example.foodpreference.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ItemImgController {
  private final ItemImgService itemImgService;

  @PostMapping("admin/imgUpload")
  public ResponseEntity<?> imgUpload(
          @RequestParam(value = "image", required = false) MultipartFile file
  ) {
    Map<String, Object> fileInfo = new HashMap<>();

    try {
      if (file != null) {
        fileInfo = itemImgService.imgTmpSave(file);
      }
    } catch (RuntimeException e) {
      log.error("ItemImgController error : "+e.getMessage());
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok(fileInfo);
  }
}
