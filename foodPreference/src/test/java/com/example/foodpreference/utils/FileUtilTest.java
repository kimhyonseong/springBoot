package com.example.foodpreference.utils;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FileUtilTest {
  @Test
  void fileNameTest() {
    String fileName = "image1.jpg";
    String tmpName;

    FileUtil fileUtil = new FileUtil();
    tmpName = fileUtil.makeFileName(fileName);

    System.out.println(tmpName);
  }
}