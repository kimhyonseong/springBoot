package com.example.foodpreference.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Component
public class FileUtil {
    public String makeFileName(String originName) {
        String fileName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        int expPos = originName.lastIndexOf(".");
        fileName += originName.substring(expPos,originName.length());

        return fileName;
    }

    public Map<String,Object> fileInfo(MultipartFile file) {
        final List<String> ALLOW_TYPE = Arrays.asList("image/jpeg","image/png");
        Map<String, Object> fileInfo = new HashMap<>();
        System.out.println("fileInfo");

        try {
            System.out.println(file.getContentType());

            // 정보들 map에 저장
            //System.out.println(Arrays.toString(file.getBytes()));
            System.out.println(file.getOriginalFilename());
        } catch (Exception e) {
            log.error(e.getMessage());
            log.error("업로드 불가능 파일 " + file.getName());
            fileInfo.put("status","error");
        }

        return fileInfo;
    }
}
