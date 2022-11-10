package com.example.foodpreference.restController;

import com.example.foodpreference.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class ItemImgController {

  @PostMapping("admin/imgUpload")
  public ResponseEntity<?> imgUpload(
          MultipartFile file
  ) {
    Map<String, Object> fileInfo = new HashMap<>();
    try {
      FileUtil fileUtil = new FileUtil();
      fileInfo = fileUtil.uploadFileInfo(file);

      if (fileInfo.get("status") == "error") {
        throw new RuntimeException("file error");
      }

    } catch (RuntimeException e) {
      log.error("ItemImgController error : "+e.getMessage());
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.ok(fileInfo);
  }
}
