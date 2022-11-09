package com.example.foodpreference.restController;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ItemImgController {

  @PostMapping("admin/imgUpload")
  public ResponseEntity<?> imgUpload(
          MultipartFile file
  ) {
    return null;
  }
}
